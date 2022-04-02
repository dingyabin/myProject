package com.dingyabin.config;

/**
 * @author 丁亚宾
 * Date: 2022/4/3.
 * Time:0:21
 */
public class ApolloConfigServiceTest {


    public static void main(String[] args) throws InterruptedException {
        ApolloConfigService.getInstance().withNs("application.properties,King.adapter.select.config.json");

        while (true) {
            String property = ApolloConfigService.getProperty("application", "key", "0");
            System.out.println(property);

            String xxx = ApolloConfigService.getContent("King.adapter.select.config", "xxx");
            System.out.println(xxx);

            Thread.sleep(1000);
        }

    }
}
