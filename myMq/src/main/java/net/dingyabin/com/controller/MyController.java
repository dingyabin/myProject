package net.dingyabin.com.controller;

import net.dingyabin.com.bean.Student;
import net.dingyabin.com.service.StudentProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by MrDing
 * Date: 2017/3/4.
 * Time:23:05
 */
@Controller
@RequestMapping("/mq")
public class MyController {

    @Resource
    private StudentProducer studentProducer;

    @RequestMapping(value="/send", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String sendStudent(@RequestBody Student student){
        for (int i = 0; i <50 ; i++) {
            student.setId(i);
            student.setName("你好");
            studentProducer.send("myFirstQuene",student);
            studentProducer.send("mysecondQuene","这是测试类"+i);
        }
        return "success";
    }

}
