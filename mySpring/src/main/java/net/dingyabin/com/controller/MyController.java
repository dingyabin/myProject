package net.dingyabin.com.controller;

import net.dingyabin.com.bean.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:11:53
 */
@Controller
public class MyController {


    @RequestMapping("/test")
    @ResponseBody
    public Student convertion(Student stu){
        return stu;
    }
}
