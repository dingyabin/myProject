package com.example.scheduler.job.task;

import com.xxl.job.core.context.XxlJobHelper;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 丁亚宾
 * Date: 2022/5/9.
 * Time:22:30
 */
@Component
public class JobTask {


    @com.xxl.job.core.handler.annotation.XxlJob("PrintTask")
    public void printTask() {
        String jobParam = XxlJobHelper.getJobParam();
        System.out.println("xxxxxxxxxxxxxxxxx " + jobParam + new Date());
    }


}
