package io.babyredis.error;

public class BabyRedisException extends RuntimeException {
    public BabyRedisException(String message) {
        super(message);
    }

    public BabyRedisException(String message, Throwable cause) {
        super(message, cause);
    }

    public BabyRedisException(Throwable cause) {
        super(cause);
    }
}
