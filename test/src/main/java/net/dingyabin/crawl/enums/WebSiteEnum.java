package net.dingyabin.crawl.enums;

import net.dingyabin.bean.SoftDownload;
import net.dingyabin.bean.TorrentDownload;
import net.dingyabin.crawl.consumer.ExcelTorrentConsumer;
import net.dingyabin.crawl.consumer.SimpleTorrentConcumer;
import org.joda.time.DateTime;

/**
 * Created by MrDing
 * Date: 2018/7/29.
 * Time:0:50
 */
public enum WebSiteEnum {


    /**
     * 电影天堂
     */
    DYTT(".txt", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),

    /**
     * 第一会所
     */
    DYHS(".torrent", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),

    /**
     * 2048
     */
    E048(".txt", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),

    /**
     * ASMR
     */
    ASMR(".mp3", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),


    TWO66NA(".txt", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\xiiixiii\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),

    LPXXS(".mp3", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\jqsm\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),

    M3U8(".ts", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\m3u8u\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),

    CAITAZHIJIA(".mp4", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\caitazhijia\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),

    YUELAING(".txt", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\yueliang\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),


    FOSTWARE(".xlsx", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\software\\", System.getenv().get("USERNAME"), "20240103")){
        @Override
        public SimpleTorrentConcumer consumer() {
            return new ExcelTorrentConsumer(SoftDownload.class);
        }
    },

    CILICAO(".xlsx", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))){
        @Override
        public SimpleTorrentConcumer consumer() {
            return new ExcelTorrentConsumer(TorrentDownload.class);
        }
    },

    CAITADIYI(".txt", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\CAITADIYI\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"))),

    ;


    private String fileType;

    private String path;

    WebSiteEnum(String fileType, String path) {
        this.fileType = fileType;
        this.path = path;
    }

    public String getFileType() {
        return fileType;
    }

    public String getPath() {
        return path;
    }

    public SimpleTorrentConcumer consumer(){
        return new SimpleTorrentConcumer();
    }
}
