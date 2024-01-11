package net.dingyabin.crawl.producer;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import net.dingyabin.bean.FileResult;
import net.dingyabin.crawl.request.AbstractRequest;
import net.dingyabin.utils.AES;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 丁亚宾
 * Date: 2020/10/24.
 * Time:3:56
 */
public class TempTxtProducer extends AbstractRequest {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(3);

    private static ExecutorService executorService = Executors.newFixedThreadPool(100);

    private String torrentPath;

    private String moviePath;

    private String key;


    @Override
    protected Map<String, String> getRequestHeader() {
        return null;
    }


    @Override
    protected int getConnTimeOut() {
        return 20;
    }


    @Override
    protected int getReadTimeOut() {
        return 60;
    }


    public TempTxtProducer(String torrentPath, String moviePath) {
        this.torrentPath = torrentPath;
        this.moviePath = moviePath;
    }


    public FileResult download() {
        FileResult fileResult = new FileResult();
        try {

            String[] split = torrentPath.split("=");

            String movieName = split[0].trim();
            fileResult.setMoviePath(moviePath + movieName);
            fileResult.setMovieName(movieName);


            String indexM3u8Url = split[1].trim();

            String baseUrl = getBaseUrl(indexM3u8Url);
            if(StringUtils.isBlank(baseUrl)){
                System.out.println("获取baseUrl失败, name= " + movieName + "indexM3u8Url=" + indexM3u8Url);
                return null;
            }

            String m3u8Content = getM3u8ContentByUrl(indexM3u8Url);
            if (StringUtils.isBlank(m3u8Content)){
                System.out.println("获取m3u8内容失败, name= " + movieName);
                return null;
            }
            //按照行读取m3u8内容
            LineIterator lineIterator = IOUtils.lineIterator(new ByteArrayInputStream(m3u8Content.getBytes()), "UTF-8");

            List<String> parts = Lists.newArrayList();

            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                if (line.startsWith("#EXT-X-KEY:METHOD=AES-128")) {
                    String keyUrl = baseUrl + StringUtils.substringAfter(line, "URI=").replaceAll("\"", "");
                    key = getStringResource(keyUrl, "utf-8");
                    if (key == null) {
                        System.out.println("获取key失败, name= " + movieName + "indexM3u8Url=" + indexM3u8Url);
                        return null;
                    }
                    continue;
                }
                if (line.startsWith("/") && line.endsWith(".ts")) {
                    parts.add(baseUrl + line);
                }
            }
            fileResult.start(parts.size());
            for (int i = 0; i < parts.size(); i++) {
                String downLoadFilePath = fileResult.getMoviePath() + File.separator + i + ".ts";
                fileResult.addPart(downLoadFilePath);
                doDownloadAsync(parts.get(i), downLoadFilePath, fileResult);
            }
            return fileResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileResult;
    }


    /**
     * 根据index的url获取m3u8内容
     * @param indexM3u8Url indexM3u8Url
     * @return m3u8内容
     */
    private String getM3u8ContentByUrl(String indexM3u8Url) {
        try {
            return getStringResource(indexM3u8Url, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取baseUrl
     * @param longUrl longUrl
     * @return baseUrl
     */
    private String getBaseUrl(String longUrl) {
        try {
            URL url = new URL(longUrl);
            return url.getProtocol() + "://" + url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 异步下载
     */
    private void doDownloadAsync(String url, String filePath, FileResult fileResult) {
        TempTxtProducer.executorService.execute(() -> {

            try {
                File target = new File(filePath);
                if (target.exists()) {
                    fileResult.getCountDownLatch().countDown();
                    System.out.println("exist " + fileResult.getMoviePath() + File.separator + target.getName());
                    return;
                }
                byte[] fileResource = getFileResource(url);
                if (fileResource == null) {
                    System.out.println("下载失败：" + filePath + " 即将重试...");
                    //失败了，记录下
                    fileResult.addFailUrl(url);
                    //重新扔到队列里下载
                    doDownloadAsync(url, filePath, fileResult);
                    return;
                } else {
                    fileResource = AES.decrypt(fileResource, key);
                    System.out.println("下载完成：" + filePath);
                    //成功了，计数减一,删除失败记录
                    fileResult.getCountDownLatch().countDown();
                    fileResult.removeFailUrl(url);
                }
                FileUtils.writeByteArrayToFile(target, fileResource);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.printf("还剩%s个任务\n", fileResult.getCountDownLatch().getCount());
            }
        });
    }


    private List<String> createScriptAndRun(String moviePath, String movieName) {
        try {
            String scriptPath = moviePath + File.separator + "run.bat";
            String scriptContent = String.format("copy/b  %s*.ts  %s%s.mp4", moviePath + File.separator, moviePath + File.separator, movieName);
            FileUtils.write(new File(scriptPath), scriptContent, "GBK");
            Process exec = Runtime.getRuntime().exec(scriptPath);
            InputStream inputStream = exec.getInputStream();
            List<String> lines = IOUtils.readLines(inputStream, "GBK");
            lines.forEach(System.out::println);
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    /**
     * 会阻塞
     */
    public void waitToFinish(FileResult fileResult) {
        try {
            fileResult.getCountDownLatch().await();

            if (fileResult.getFailUrls().size() > 0) {
                System.out.println("有下载失败的...");
                //return;
            }
//            List<String> scriptAndRun = createScriptAndRun(fileResult.getMoviePath(), fileResult.getMovieName());
//            if (scriptAndRun.size() > 2) {
//                File partFile;
//                for (String part : fileResult.getParts()) {
//                    partFile = new File(part);
//                    partFile.delete();
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected boolean needRetry() {
        return false;
    }


    @Override
    protected RateLimiter getRateLimiter() {
        return RATE_LIMITER;
    }


    public static void main(String[] args) {
        try {
            TempTxtProducer txtProducer = new TempTxtProducer("【艾琳S175】性感大长腿踩虐肚脐 = https://88.xn--bw2a68j.com/20231207/D127KLF3/2145kb/hls/index.m3u8", "F:\\IE下载\\下载测试\\");
            FileResult fileResult = txtProducer.download();
            if (fileResult == null) {
                System.out.println("下载失败...............................");
                return;
            }
            executorService.shutdown();
            txtProducer.waitToFinish(fileResult);
            executorService.awaitTermination(1, TimeUnit.HOURS);
            System.out.println("^_^_^_^_^_^^_^_^任务完成^_^^_^_^^_^_^^_^_^");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




//    public static void main(String[] args) {
//        try {
//            List<String> strings = IOUtils.readLines(TempTxtProducer.class.getResourceAsStream("/file.txt"), "utf-8");
//            for (String string : strings) {
//                if (StringUtils.isBlank(string)) {
//                    continue;
//                }
//                TempTxtProducer txtProducer = new TempTxtProducer(string, "E:\\test\\ttt\\");
//                FileResult fileResult = txtProducer.download();
//                if (fileResult == null) {
//                    System.out.println("下载key失败，:"+ string);
//                    continue;
//                }
//                TempTxtProducer.executorService.execute(() -> txtProducer.waitToFinish(fileResult));
//            }
//
//            TempTxtProducer.executorService.awaitTermination(10, TimeUnit.HOURS);
//
//            System.out.println("^_^_^_^_^_^^_^_^任务完成^_^^_^_^^_^_^^_^_^");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
