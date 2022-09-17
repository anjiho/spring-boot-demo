package com.example.demo.enums;

public enum ErrorCodeEnum {

    SYSTEM_ERROR(999, "오류가 발생하였습니다. 잠시후 재시도 부탁드립니다.") // System Code
    , SUCCESS_OK(200, "success") // 성공
    , ACCESS_DENIED(403, "accessDenied") //
    , PAGE_NOT_FOUND(404, "PAGE NOT FOUND") //


    ,CUSTOM_RESULT_NULL_LIST(1001, "결과 리스트가 없습니다.")

    , IP_ACCESS_DENY_ERROR(1102, "IP 접근 권한이 없습니다.")

    , REST_API_CALL_ERROR(1201, "API 통신시 알 수 없는 에러가 발생되었습니다.")    //API통신 에러



    // client에서 무시하는 오류 코드 9000번대
    , IGNORE_ERROR(9000, "systemerror") //

    // AES
    , ERROR_AESCODEC(991,"AES encode/decode Error")

    //사용자 노출 에러 메시지
    , USER_NOTI_ERROR(1000,"")

    //end
    ;

    private int code;
    private String desc;

    ErrorCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }

    public static String getEventErrorMessage(int code) {
        for (ErrorCodeEnum errCode : ErrorCodeEnum.values()) {
            if (code == errCode.code) {
                return errCode.getDesc();
            }
        }
        return null;
    }

}
