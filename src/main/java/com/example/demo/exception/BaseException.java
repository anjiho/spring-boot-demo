package com.example.demo.exception;

import com.example.demo.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -1656188798522503941L;
    private ErrorCodeEnum resultCode;
    private String resultMessage;

    private Object errorData;

    public BaseException() {
        super();
        this.resultMessage = ErrorCodeEnum.SYSTEM_ERROR.getDesc();
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultMessage = message;
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.resultMessage = message;
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(String message) {
        super(message);
        this.resultMessage = message;
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.resultCode = ErrorCodeEnum.SYSTEM_ERROR;
    }

    public BaseException(String message, ErrorCodeEnum errorCode){
        super(message);

        this.resultCode = errorCode;
        this.resultMessage = message;

    }

    public BaseException(String message, ErrorCodeEnum errorCode, Object errorData){
        super(message);

        this.resultCode = errorCode;
        this.resultMessage = message;
        this.errorData = errorData;
    }
}
