package net.dingyabin.com.controller;

import com.google.common.collect.ImmutableList;
import net.dingyabin.com.bean.EmailBean;
import net.dingyabin.com.bean.Student;
import net.dingyabin.com.result.Response;
import net.dingyabin.com.service.MailService;
import net.dingyabin.com.service.MyService;
import net.dingyabin.com.utils.Config;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:11:53
 */
@Controller
public class MyController {

    Logger logger = LoggerFactory.getLogger(MyController.class);

    @Resource(name = "myService")
    private MyService myService;


    @Resource
    private MailService mailService;


    @RequestMapping(value = "/test", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Student convertion(@RequestBody Student student) {
        System.out.println(Config.getAsString("test"));
        System.out.println(Config.getAsInteger("number"));
        return student;
    }


    @RequestMapping(value = "/test2", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Student convertion2(Student student) {
        String hello = myService.done("hello");
        System.out.println(hello);
        student.setName("Jim");
        System.out.println(Config.getAsString("test"));
        System.out.println(Config.getAsInteger("number"));
        return student;
    }


    /**
     * 发送email
     *
     * @return
     */
    @RequestMapping(value = "/test3/{to}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Response convertion3(@PathVariable("to") String to) {
        StopWatch watch = StopWatch.createStarted();//开始计时
        EmailBean emailBean = new EmailBean();
        emailBean.setSubject("This is a test email");
        emailBean.setContent("<body><p>Hello Html Email</p><img src='cid:name.PNG'/></body>");
        emailBean.setCreateTime(new Date());
        emailBean.setToWhere(new String[]{to});
        emailBean.setFromName(Config.getAsString("mail.username"));
        //封装附件
        List<File> attachmentList = ImmutableList.of(new File("C:\\Users\\MrDing\\Desktop\\知识分享.doc"));
        emailBean.setAttachment(attachmentList);
        //封装内联图片
        List<File> inlineList = ImmutableList.of(new File("C:\\Users\\MrDing\\Desktop\\name.PNG"));
        emailBean.setInline(inlineList);
        //开始发送
        boolean success = mailService.sendMail(emailBean);
        watch.stop();//结束计时
        System.out.println("发送完成，用时：" + watch.getTime());
        if (success) {
            return Response.ok();
        }
        return Response.error();
    }


}
