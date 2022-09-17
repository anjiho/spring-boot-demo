package com.example.demo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseWrapResponse implements Serializable {

    private static final long serialVersionUID = 9080171113595953078L;

    private String resultMessage;
    private int resultCode;

    private Object data;
}
