package com.king.dubbo.impl;

import com.king.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author 丁亚宾
 * Date: 2022/5/17.
 * Time:20:30
 */
@DubboService(registry = "nacos", version = "0.0.1", group = "first")
public class HelloServiceImpl implements HelloService {


    @Override
    public String hello() {
        return "ok,thanks";
    }

}
