package net.dingyabin.com.controller;

import net.dingyabin.com.bean.Student;
import net.dingyabin.com.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger=LoggerFactory.getLogger(MyController.class);

    @RequestMapping(value="/test",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Student convertion(@RequestBody  Student student){
        System.out.println(Config.getAsString("test"));
        System.out.println(Config.getAsInteger("number"));
        return student;
    }


    @RequestMapping(value="/test2",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Student convertion2(Student student){
        student.setName("Jim");
        System.out.println(Config.getAsString("test"));
        System.out.println(Config.getAsInteger("number"));
        return student;
    }




}
