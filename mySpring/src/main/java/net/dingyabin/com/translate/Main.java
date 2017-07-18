package net.dingyabin.com.translate;


import java.io.UnsupportedEncodingException;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20170718000065084";
    private static final String SECURITY_KEY = "MLdFZYI0nRCEudzPlBt1";

    public static void main(String[] args) throws UnsupportedEncodingException {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String query = "son of bitch";
        String transResult = api.getTransResult(query, "auto", "zh");
        String target = api.paresResult(transResult);
        System.out.println(target);
    }
}
