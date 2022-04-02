package com.dingyabin.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author dingyabin
 * @date 2022-04-02 16:33
 */
@Import(CommonConfig.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableApolloConfigService {
}
