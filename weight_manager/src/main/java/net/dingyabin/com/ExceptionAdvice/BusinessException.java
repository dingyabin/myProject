package net.dingyabin.com.ExceptionAdvice;

import net.dingyabin.com.enums.BusinessEnum;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:21:02
 */
public class BusinessException extends RuntimeException {


    private String code;

    public BusinessException(String code) {
        this.code = code;
    }

    public BusinessException( String code ,String message) {
        super(message);
        this.code = code;
    }

    public BusinessException( BusinessEnum enums) {
        super(enums.getMessage());
        this.code = enums.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
