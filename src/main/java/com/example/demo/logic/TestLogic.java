package com.example.demo.logic;

import com.example.demo.dto.res.ApiResultObjectResDto;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.CommonException;
import com.example.demo.service.TestService;
import com.example.demo.utils.ValueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class TestLogic {

    @Autowired
    private TestService testService;

    public ApiResultObjectResDto getSysLogic() {
        int resultCode = ValueUtils.resultCode;
        List<String> list = testService.findSysStingList();

        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException(ErrorCodeEnum.CUSTOM_RESULT_NULL_LIST.getDesc(), ErrorCodeEnum.CUSTOM_RESULT_NULL_LIST);
        }

        return ApiResultObjectResDto.builder()
                .resultCode(resultCode)
                .result(list)
                .build();
    }
}
