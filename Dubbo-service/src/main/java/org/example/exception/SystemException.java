package org.example.exception;

public class SystemException extends RuntimeException {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SystemException(int code) {
        this.code = code;
    }

    public SystemException(String message, int code) {
        super(message);
        this.code = code;
    }

    public SystemException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public SystemException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
