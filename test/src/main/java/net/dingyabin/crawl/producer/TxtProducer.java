package net.dingyabin.crawl.producer;

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
            String url = "";
            String baseUrl ="E:\\test\\";
            File file = new File("C:\\Users\\丁亚宾\\Desktop\\torrent\\20201024\\yueliang\\台湾SWAG满足邻居哥哥饥渴的足交的射精游戏.txt");

            fileResult.setMoviePath(baseUrl);
            fileResult.setMovieName(FilenameUtils.getBaseName(file.getPath()));

            LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8");
            while (lineIterator.hasNext()) {
                String line = lineIterator.next();
                if (line.startsWith("http")) {
                    url = line;
                    continue;
                }
                if (line.endsWith(".ts")) {

                    String resourceuri = url + "/" + line;

                    String downLoadFilePath = baseUrl +line;

                    fileResult.addPart(downLoadFilePath);

                    doDownload(resourceuri, downLoadFilePath, fileResult);
                }
            }
            return fileResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileResult;
    }


    private void doDownload(String url, String filePath, FileResult fileResult) {
        executorService.execute(() -> {

            try {
                File target = new File(filePath);
                if (target.exists()) {
                    System.out.println("exist " + target.getName());
                    return;
                }
                byte[] fileResource = getFileResource(url);
                if (fileResource == null) {
                    fileResult.addFailUrl(url);
                    doDownload(url, filePath, fileResult);
                    return;
                } else {
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
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.HOURS);

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
            System.out.println("^_^_^_^_^_^^_^_^任务完成^_^^_^_^^_^_^^_^_^");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
