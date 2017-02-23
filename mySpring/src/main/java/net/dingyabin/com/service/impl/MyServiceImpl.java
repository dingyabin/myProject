package net.dingyabin.com.service.impl;

import net.dingyabin.com.annotation.MyAnnotation;
import net.dingyabin.com.enums.Sex;
import net.dingyabin.com.service.MyService;
import org.springframework.stereotype.Service;

/**
 * Created by MrDing
 * Date: 2017/2/20.
 * Time:23:14
 */

@Service("myService")
@MyAnnotation(name="Jim", sex= Sex.MAN)
public class MyServiceImpl implements MyService {


    @Override
    public String done(@MyAnnotation String param) {
        return param;
    }

}
