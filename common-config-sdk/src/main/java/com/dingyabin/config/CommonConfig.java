package com.dingyabin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dingyabin
 * @date 2022-04-02 16:32
 */
@Configuration
public class CommonConfig {



    @Bean
    public ApolloConfigService apolloConfigService() {
        return ApolloConfigService.getInstance();
    }




}