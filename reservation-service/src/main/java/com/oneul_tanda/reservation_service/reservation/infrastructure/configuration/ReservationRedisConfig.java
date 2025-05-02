package com.oneul_tanda.reservation_service.reservation.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class ReservationRedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    /**
     * Redis 서버에 연결하기 위한 Lettuce 기반의 RedisConnectionFactory 빈을 생성합니다.
     *
     * @return 구성된 RedisConnectionFactory 인스턴스
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    /**
     * 문자열 기반의 Redis 연산을 위한 RedisTemplate 빈을 생성합니다.
     *
     * 키와 값을 모두 문자열로 직렬화하도록 설정되어 있으며, 지정된 Redis 연결 팩토리를 사용합니다.
     *
     * @return 문자열 키와 값을 사용하는 RedisTemplate 인스턴스
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.string());
        return template;
    }
}
