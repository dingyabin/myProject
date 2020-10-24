package net.dingyabin.crawl.producer;

import com.google.common.collect.Lists;
import net.dingyabin.bean.FileResult;
import net.dingyabin.crawl.request.AbstractRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import javax.xml.transform.Source;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 丁亚宾
 * Date: 2020/10/24.
 * Time:3:56
 */
public class TxtProducer extends AbstractRequest {


    private static ExecutorService executorService = Executors.newFixedThreadPool(20);


    @Override
    protected Map<String, String> getRequestHeader() {
        return null;
    }


    public FileResult download() {
        FileResult fileResult = new FileResult();
        try {
            String baseUrl ="E:\\test\\";
            File file = new File("C:\\Users\\丁亚宾\\Desktop\\torrent\\20201023\\yueliang\\SWAG剧情精品美乳正妹试玩道具催情与老板大战_x264_aac.txt");

            fileResult.setMoviePath(baseUrl);
            fileResult.setMovieName(FilenameUtils.getBaseName(file.getPath()));

            LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");

            String url = "";
            List<String> parts = Lists.newArrayList();
            while (lineIterator.hasNext()) {
                String line = lineIterator.next();
                if (line.startsWith("http")) {
                    url = line;
                    continue;
                }
                if (line.endsWith(".ts")) {
                    parts.add(line);
                }
            }
            fileResult.start(parts.size());
            for (String part : parts) {
                String resourceuri = url + "/" + part;

                String downLoadFilePath = baseUrl + part;

                fileResult.addPart(downLoadFilePath);

                doDownloadAsync(resourceuri, downLoadFilePath, fileResult);
            }
            return fileResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileResult;
    }



    /**
     * 异步下载
     */
    private void doDownloadAsync(String url, String filePath, FileResult fileResult) {
        executorService.execute(() -> {

            try {
                File target = new File(filePath);
                if (target.exists()) {
                    fileResult.getCountDownLatch().countDown();
                    System.out.println("exist " + target.getName());
                    return;
                }
                byte[] fileResource = getFileResource(url);
                if (fileResource == null) {
                    System.out.println("下载失败："+ filePath+" 即将重试...");
                    //失败了，记录下
                    fileResult.addFailUrl(url);
                    //重新扔到队列里下载
                    doDownloadAsync(url, filePath, fileResult);
                    return;
                } else {
                    System.out.println("下载完成："+ filePath);
                    //成功了，计数减一,删除失败记录
                    fileResult.getCountDownLatch().countDown();
                    fileResult.removeFailUrl(url);
                }
                FileUtils.writeByteArrayToFile(target, fileResource);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }












    public List<String> createScriptAndRun(String scritPath, String moviePath, String movieName) {
        try {
            String scriptPath = scritPath +"run.bat";
            String scriptContent = String.format("copy/b  %s*.ts  %s%s.mp4", moviePath, moviePath, movieName);
            FileUtils.write(new File(scriptPath), scriptContent, "GBK");
            Process exec = Runtime.getRuntime().exec(scriptPath);
            InputStream inputStream = exec.getInputStream();
            List<String> lines = IOUtils.readLines(inputStream, "GBK");
            lines.forEach(System.out::println);
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }



    public static void main(String[] args) {
        try {
            TxtProducer txtProducer = new TxtProducer();
            FileResult fileResult = txtProducer.download();

            fileResult.getCountDownLatch().await();

            if (fileResult.getFailUrls().size() > 0){
                System.out.println("有下载失败的...");
                return;
            }
            List<String> scriptAndRun = txtProducer.createScriptAndRun("E:\\test\\", fileResult.getMoviePath(), fileResult.getMovieName());
            if (scriptAndRun.size() > 2) {
                File partFile;
                for (String part : fileResult.getParts()) {
                    partFile = new File(part);
                    partFile.delete();
                }
            }

            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.HOURS);

            System.out.println("^_^_^_^_^_^^_^_^任务完成^_^^_^_^^_^_^^_^_^");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
