package com.example.demo.controller.rest;

import com.example.demo.common.BaseWrapResponse;
import com.example.demo.dto.res.MethodArgumentErrorResponse;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.enums.MethodArgErrorCodeEnum;
import com.example.demo.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlingRestControllerAdvice {
    @Autowired
    private ApplicationContext applicationContext;

    @ExceptionHandler(Throwable.class)
    public BaseWrapResponse handleException(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        return makeErrorResponse(ex);
    }

    @ExceptionHandler(Exception.class)
    public BaseWrapResponse handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        //HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(CustomExceptionHandlerExceptionResolver.ORIGINAL_OCCURRED_EXCEPTION_HANDLER_ATTR);
        //ResponseWrapExclude methodAnnotation = handlerMethod.getMethodAnnotation(ResponseWrapExclude.class);

        log.error(ex.getMessage(), ex);

        BaseWrapResponse bwResponse = makeErrorResponse(ex);

        return bwResponse;
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public BaseWrapResponse handleException(HttpServletRequest request, HttpServletResponse response, HttpStatusCodeException ex) {
        //HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(CustomExceptionHandlerExceptionResolver.ORIGINAL_OCCURRED_EXCEPTION_HANDLER_ATTR);
        //ResponseWrapExclude methodAnnotation = handlerMethod.getMethodAnnotation(ResponseWrapExclude.class);

        log.error(ex.getMessage(), ex);

        BaseWrapResponse bwResponse = makeErrorResponse(ex);

        return bwResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentErrorResponse> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException 발생!!! url:{}, trace:{}", request.getRequestURI(), e.getStackTrace());
        MethodArgumentErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());
        return new ResponseEntity<MethodArgumentErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private BaseWrapResponse makeErrorResponse(Throwable ex) {
        BaseWrapResponse bwResponse = new BaseWrapResponse();

        ErrorCodeEnum resultCodeEnum;
        String msg;
        Object errorData = null;

        if (ex instanceof BaseException) {
            resultCodeEnum = ((BaseException) ex).getResultCode();
            msg = ((BaseException) ex).getResultMessage();
            errorData = ((BaseException) ex).getErrorData();
        } else {
            resultCodeEnum = ErrorCodeEnum.SYSTEM_ERROR;
            msg = StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : ErrorCodeEnum.SYSTEM_ERROR.getDesc();
        }

        if (resultCodeEnum.getCode() == ErrorCodeEnum.USER_NOTI_ERROR.getCode()) {
            assert (StringUtils.isNotBlank(ex.getMessage()));
            msg = ex.getMessage();
        } else {
            //msg = resultCodeEnum.getDesc() + " : " + msg;
            msg = resultCodeEnum.getDesc();
        }
        bwResponse.setResultCode(resultCodeEnum.getCode());
        bwResponse.setResultMessage(msg);

        if (errorData != null) {
            bwResponse.setData(errorData);
        }

        return bwResponse;
    }

    private MethodArgumentErrorResponse makeErrorResponse(BindingResult bindingResult) {
        String resultCode = "";
        String description = "";
        String resultMessage = "";

        //에러가 있다면
        if (bindingResult.hasErrors()) {
            //DTO에 설정한 meaasge값을 가져온다
            resultMessage = bindingResult.getFieldError().getDefaultMessage();

            //DTO에 유효성체크를 걸어놓은 어노테이션명을 가져온다.
            String bindResultCode = bindingResult.getFieldError().getCode();

            switch (bindResultCode) {
                case "NotNull":
                    resultCode = MethodArgErrorCodeEnum.NOT_NULL.getCode();
                    description = MethodArgErrorCodeEnum.NOT_NULL.getDescription();
                    break;
                case "NotBlank":
                    resultCode = MethodArgErrorCodeEnum.NOT_BLANK.getCode();
                    description = MethodArgErrorCodeEnum.NOT_BLANK.getDescription();
                    break;
                case "Min":
                    resultCode = MethodArgErrorCodeEnum.MIN_VALUE.getCode();
                    description = MethodArgErrorCodeEnum.MIN_VALUE.getDescription();
                    break;
            }
            log.error("Method Argument Error Code {}", resultCode);
            log.error("Method Argument Error Message {}", resultMessage);
        }

        return new MethodArgumentErrorResponse(resultCode, description, resultMessage);
    }
}