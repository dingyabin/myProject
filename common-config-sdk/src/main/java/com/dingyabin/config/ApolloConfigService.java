package com.dingyabin.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.dingyabin.config.listener.ApollOnChangeListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dingyabin
 * @date 2022-04-02 14:46
 */
public class ApolloConfigService implements SmartInitializingSingleton {

    private static final ApolloConfigService INSTANCE = new ApolloConfigService();

    private static final Map<String, Config> PROPERTIES_CONFIG_MAP = new HashMap<>();

    private static final Map<String, ConfigFile> JSON_CONFIG_MAP = new HashMap<>();

    public static final Map<String, ApollOnChangeListener> LISTENERS = new HashMap<>();

    @Value("${apollo.ns.config}")
    private String ns;

    private ApolloConfigService(){
    }


    /**
     * 单例模式
     * @return ApolloConfigService
     */
    public static ApolloConfigService getInstance() {
        return INSTANCE;
    }


    @Override
    public void afterSingletonsInstantiated() {
        for (String s : ns.split(",")) {
            String formatNs = StringUtils.substringBeforeLast(s, ".");
            if (s.endsWith(".yml") || s.endsWith(".yaml")) {
                Config config = ConfigService.getConfig(s);
                addListener(formatNs, config);
                PROPERTIES_CONFIG_MAP.put(formatNs, config);
                continue;
            }
            if (s.endsWith(".json")) {
                ConfigFile configFile = ConfigService.getConfigFile(formatNs, ConfigFileFormat.JSON);
                JSON_CONFIG_MAP.put(formatNs, configFile);
                continue;
            }
            //其余的按照property文件处理
            Config config = ConfigService.getConfig(formatNs);
            addListener(formatNs, config);
            PROPERTIES_CONFIG_MAP.put(formatNs, config);
        }

    }


    private static void addListener(String formatNs, Config config) {
        ApollOnChangeListener apollOnChangeListener = LISTENERS.get(formatNs);
        if (apollOnChangeListener == null) {
            apollOnChangeListener = LISTENERS.get("*");
        }
        if (apollOnChangeListener != null) {
            config.addChangeListener(apollOnChangeListener, apollOnChangeListener.interestedKeys(), apollOnChangeListener.interestedKeyPrefixs());
        }
    }


    public static String getProperty(String ns, String key, String defaultValue) {
        Config config = PROPERTIES_CONFIG_MAP.get(ns);
        if (config == null) {
            return defaultValue;
        }
        return config.getProperty(key, defaultValue);
    }


    public static String getContent(String ns, String defaultValue) {
        ConfigFile configFile = JSON_CONFIG_MAP.get(ns);
        if (configFile == null) {
            return defaultValue;
        }
        return configFile.getContent();
    }


    public ApolloConfigService withNs(String ns) {
        this.ns = ns;
        afterSingletonsInstantiated();
        return this;
    }


}