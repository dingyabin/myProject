package net.dingyabin.com.enums;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:21:03
 */
public enum BusinessEnum {
    ALREADYEXIST("501","今天已经添加过了哦！"),
    SYSTEM_BUSY("500","系统繁忙，请稍后重试.....");

    private String code;

    private String message;

    BusinessEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
