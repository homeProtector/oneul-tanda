package com.oneul_tanda.flight_service.domain.exception.lock;

import com.oneul_tanda.flight_service.domain.exception.common.ErrorMessage;

public class LockFailedAfterRetryException extends RuntimeException {
    public LockFailedAfterRetryException() {super(ErrorMessage.LOCK_FAILED_AFTER_RETRY.getMessage());}
}
