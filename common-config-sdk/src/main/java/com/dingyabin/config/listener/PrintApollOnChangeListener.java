package com.dingyabin.config.listener;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.dingyabin.config.listener.ApollOnChangeListener;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author dingyabin
 * @date 2022-04-02 15:42
 */

@Component
public class PrintApollOnChangeListener implements ApollOnChangeListener {


    @Override
    public String interestedNs() {
        return "*";
    }

    @Override
    public Set<String> interestedKeys() {
        return Sets.newHashSet("key");
    }


    @Override
    public Set<String> interestedKeyPrefixs() {
        return null;
    }


    @Override
    public void onChange(ConfigChangeEvent changeEvent) {
        Set<String> set = changeEvent.changedKeys();
        for (String key : set) {
            ConfigChange change = changeEvent.getChange(key);
            System.out.println(String.format("found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
        }
    }


}