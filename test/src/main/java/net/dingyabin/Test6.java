package net.dingyabin;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.google.common.util.concurrent.RateLimiter;
import net.dingyabin.crawl.utils.Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author Administrator
 * Date: 2023/7/29.
 * Time:22:21
 */
public class Test6 {

//    private static final RateLimiter RATE_LIMITER = RateLimiter.create(3);

    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    private static AtomicInteger count = new AtomicInteger(34883);



    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                register();
            });
        }
        executorService.shutdown();
    }





    private static void register() {
        while (true) {
            try {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("enews", "register");
                paramMap.put("groupid", "1");
                paramMap.put("tobind", "0");

                String name = RandomStringUtils.randomAlphanumeric(7, 10);
                String password = RandomStringUtils.randomAlphanumeric(5, 8) + RandomStringUtils.randomNumeric(4);
                paramMap.put("username", name);
                paramMap.put("password", password);
                paramMap.put("repassword", password);
                paramMap.put("Submit", "马上注册");

                HttpRequest httpRequest = HttpRequest.post("https://caita3456.com/e/member/doaction.php").form(paramMap).timeout(HttpGlobalConfig.getTimeout()).disableCookie();
                getRequestHeader().forEach(httpRequest::header);
                String result = httpRequest.execute().body();
                if (result.contains("注册成功")) {
                    System.out.println(count.incrementAndGet() + "次注册成功: " + name + "------" + password);
                } else {
                    System.out.println(result);
                }
                Thread.sleep(50 + RandomUtils.nextInt(15, 200));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






    protected static Map<String, String> getRequestHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0");
        header.put("x-forwarded-for", Utils.getRandomIp());
        return header;
    }

}
