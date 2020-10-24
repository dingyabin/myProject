package net.dingyabin.bean;

import lombok.Data;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author 丁亚宾
 * Date: 2020/10/24.
 * Time:5:23
 */
@Data
public class FileResult {

    private String movieName;

    private String moviePath;

    private Set<String> failUrls = Collections.synchronizedSet(new HashSet<>());

    private List<String> parts = new ArrayList<>();

    private CountDownLatch countDownLatch;


    public FileResult() {
    }

    public void start(int count) {
        this.countDownLatch = new CountDownLatch(count);
    }


    public FileResult addPart(String part){
        parts.add(part);
        return this;
    }


    public FileResult addFailUrl(String failUrl){
        failUrls.add(failUrl);
        return this;
    }


    public void removeFailUrl(String failUrl){
        failUrls.remove(failUrl);
    }
}
