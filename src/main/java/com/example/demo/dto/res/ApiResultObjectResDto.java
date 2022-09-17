package com.example.demo.dto.res;

import com.example.demo.enums.ErrorCodeEnum;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResultObjectResDto implements Serializable {

    private static final long serialVersionUID = 3799147531985378033L;

    private int resultCode;

    private Object result;

    private String resultMessage;

    @Builder
    public ApiResultObjectResDto(Object result, int resultCode) {
        this.resultCode = resultCode;
        this.result = result == null ? "" : result;
        this.resultMessage = ErrorCodeEnum.getEventErrorMessage(resultCode) == null ? "SUCCESS" : ErrorCodeEnum.getEventErrorMessage(resultCode);
    }

    public ApiResultObjectResDto(Object result, int resultCode, String traceNo) {
        this.resultCode = resultCode;
        this.result = result == null ? "" : result;
        this.resultMessage = ErrorCodeEnum.getEventErrorMessage(resultCode) == null ? "SUCCESS" : ErrorCodeEnum.getEventErrorMessage(resultCode);
    }
}
