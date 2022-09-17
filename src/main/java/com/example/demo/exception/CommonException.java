package com.example.demo.exception;

import com.example.demo.enums.ErrorCodeEnum;

public class CommonException extends BaseException {

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, ErrorCodeEnum errorCode){
        super(message);
    }

    public CommonException(String message, ErrorCodeEnum errorCode, Object errorData){
        super(message);
    }
}
