package net.dingyabin.crawl.enums;

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


    TWO66NA(".txt", String.format("C:\\Users\\%s\\Desktop\\torrent\\%s\\", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd")));


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
}
