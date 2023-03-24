package com.example.scheduler.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 丁亚宾
 * Date: 2022/5/9.
 * Time:22:12
 */
@Configuration
public class JobConfig {




    @Bean
    @ConfigurationProperties(prefix = "xxl.job.executor")
    public XxlJobSpringExecutor xxlJobExecutor() {
        return new XxlJobSpringExecutor();
    }




}
