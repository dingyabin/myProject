package net.dingyabin.crawl.request;

import net.dingyabin.crawl.utils.Utils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrDing
 * Date: 2018/7/29.
 * Time:17:20
 */
public class AbstractRequest {

    /**
     * 设置请求头信息,子类继承的时候最好先super.getRequestHeader()一下
     * @return header
     */
    protected Map<String, String> getRequestHeader() {
        Map<String,String> header=new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0");
        header.put("x-forwarded-for", Utils.getRandomIp());
        return header;
    }

    /**
     * 获取ConnTimeOut，单位是秒(s)
     * @return ConnTime
     */
    protected int getConnTimeOut() {
        return -1;
    }

    /**
     * 获取ReadTimeOut，单位是秒(s)
     * @return ReadTimeOut
     */
    protected int getReadTimeOut() {
        return -1;
    }


    protected String getStringResource(String curl, String encoding) throws IOException {
        InputStream inputStream = getInputStream(curl);
        String gbk = IOUtils.toString(inputStream, encoding == null ? "utf-8" : encoding);
        IOUtils.closeQuietly(inputStream);
        return gbk;
    }


    protected byte[] getFileResource(String url) throws IOException {
        InputStream inputStream = getInputStream(url);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        IOUtils.closeQuietly(inputStream);
        return bytes;
    }


    private InputStream getInputStream(String curl) throws IOException {
        HttpURLConnection e = (HttpURLConnection) (new URL(curl)).openConnection();
        Map<String, String> header = getRequestHeader();
        if (header != null) {
            header.forEach(e::setRequestProperty);
        }
        int connTimeOut = getConnTimeOut();
        if (connTimeOut > 0) {
            e.setConnectTimeout(1000 * connTimeOut);
        }
        int readTimeOut = getReadTimeOut();
        if (readTimeOut > 0) {
            e.setReadTimeout(1000 * readTimeOut);
        }
        return e.getInputStream();
    }
}
