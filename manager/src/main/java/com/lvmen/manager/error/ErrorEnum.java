package com.lvmen.manager.error;

/**
 * 错误种类
 * Created by lvmen on 2019/10/25
 */
public enum ErrorEnum {
    ID_NOT_NULL("F001", "编号不可为空", false),
    REWARDRATE_ILLEGAL("F002", "收益率范围错误", false),
    STEPAMOUNT_ILLEGAL("F003", "投资步长需为整数", false),

    //...
    UNKNOWN("999","未知异常",false)
    ;

    private String code;
    private String message;
    private Boolean canRetry;

    ErrorEnum(String code, String message, Boolean canRetry) {
        this.code = code;
        this.message = message;
        this.canRetry = canRetry;
    }

    public static ErrorEnum getByCode(String code){
        for (ErrorEnum errorEnum : ErrorEnum.values()){
            if (errorEnum.code.equals(code)){
                return errorEnum;
            }
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getCanRetry() {
        return canRetry;
    }
}
