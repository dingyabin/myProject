package net.dingyabin.com.translate;

import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


public class HttpGet {

    private static final int SOCKET_TIMEOUT = 10000;
    private static final String GET = "GET";

    public static String get(String host, Map<String, String> params) {
        try {
            String sendUrl = getUrlWithQueryString(host, params);
            URL uri = new URL(sendUrl); // 创建URL对象
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(SOCKET_TIMEOUT); // 设置相应超时
            conn.setRequestMethod(GET);
            conn.setDoOutput(false);
            conn.setDoInput(true);
            int statusCode = conn.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Http错误码：" + statusCode);
            }
            // 读取服务器的数据
            InputStream is = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            String result = IOUtils.toString(is, encoding != null ? encoding : "utf-8");
            IOUtils.closeQuietly(is);
            IOUtils.close(conn);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }
        StringBuilder builder = new StringBuilder(url);
        builder.append(url.contains("?") ? "&" : "?");
        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value != null) { // 过滤空的key
                builder.append(i != 0 ? "&" : "");
                builder.append(String.format("%s=%s", key, encode(value)));
                i++;
            }
        }
        return builder.toString();
    }


    /**
     * 对输入的字符串进行URL编码, 即转换为%20这种形式
     *
     * @param input 原文
     * @return URL编码. 如果编码失败, 则返回原文
     */
    private static String encode(String input) {
        if (input == null) {
            return "";
        }
        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return input;
    }

}
