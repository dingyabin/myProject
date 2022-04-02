package com.dingyabin.config.listener;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.dingyabin.config.ApolloConfigService;
import org.springframework.beans.factory.InitializingBean;

import java.util.Set;

/**
 * @author dingyabin
 * @date 2022-04-02 15:29
 */
public interface ApollOnChangeListener extends ConfigChangeListener, InitializingBean {

    String interestedNs();


    Set<String> interestedKeys();


    Set<String> interestedKeyPrefixs();


    @Override
    default void afterPropertiesSet() throws Exception {
        ApolloConfigService.LISTENERS.put(interestedNs(), this);
    }


}
