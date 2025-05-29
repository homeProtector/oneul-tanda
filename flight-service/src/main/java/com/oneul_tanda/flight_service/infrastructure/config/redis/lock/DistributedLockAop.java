package com.oneul_tanda.flight_service.infrastructure.config.redis.lock;

import com.oneul_tanda.flight_service.domain.exception.lock.LockFailedAfterRetryException;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "LOCK: ";

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.oneul_tanda.flight_service.infrastructure.config.redis.lock.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
                signature.getParameterNames(),
                joinPoint.getArgs(),
                distributedLock.key());

        RLock rLock = redissonClient.getLock(key);

        // 예외 처리 및 락 획득을 재시도하는 로직
        int retryCount = 0;

        while (retryCount < distributedLock.maxRetryCount()) {
            try {
                boolean available = rLock.tryLock(distributedLock.waitTime(),
                        distributedLock.leaseTime(), distributedLock.timeUnit());

                if (available) {
                    log.info("[DistributedLock]: Lock acquired with key: {}", key);
                    try {
                        return aopForTransaction.proceed(joinPoint);
                    } finally {
                        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                            log.info("[DistributedLock]: Lock released with key: {}", key);
                            rLock.unlock();
                        }
                    }
                } else {
                    log.info("[DistributedLock]: Lock acquisition failed with key: {}", key);
                    retryCount++;
                    Thread.sleep(distributedLock.retryDelay());
                }
            } catch (InterruptedException e) {
                log.error("[DistributedLock]: Lock interrupted");
                throw new InterruptedException(e.getMessage());
            }
        }
        throw new LockFailedAfterRetryException();
    }
}
