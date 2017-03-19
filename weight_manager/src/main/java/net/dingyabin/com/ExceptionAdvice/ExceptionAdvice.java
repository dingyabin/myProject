package net.dingyabin.com.ExceptionAdvice;

import net.dingyabin.com.enums.BusinessEnum;
import net.dingyabin.com.result.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:20:45
 */

@ControllerAdvice
public class ExceptionAdvice {


    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Response handlerException(BusinessException e){
        e.printStackTrace();
       return Response.error().Code(e.getCode()).Message(e.getMessage());
    }



    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handlerException(Exception e){
        e.printStackTrace();
        return Response.error().Code(BusinessEnum.SYSTEM_BUSY.getCode()).Message(BusinessEnum.SYSTEM_BUSY.getMessage());
    }


}
