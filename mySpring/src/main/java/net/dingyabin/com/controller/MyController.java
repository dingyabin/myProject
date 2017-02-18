package net.dingyabin.com.controller;

import net.dingyabin.com.bean.Student;
import net.dingyabin.com.utils.Config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:11:53
 */
@Controller
public class MyController {


    @RequestMapping(value="/test",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Student convertion(@RequestBody  Student stu){
        System.out.println(Config.getAsString("test"));
        System.out.println(Config.getAsInteger("number"));
        return stu;
    }
}
