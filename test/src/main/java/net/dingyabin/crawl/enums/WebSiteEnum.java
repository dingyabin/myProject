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
    DYTT(".txt", WebSiteEnum.BATHPATH),

    /**
     * 第一会所
     */
    DYHS(".torrent", WebSiteEnum.BATHPATH),

    /**
     * 2048
     */
    E048(".txt", WebSiteEnum.BATHPATH),


    ASMR(".mp3",  WebSiteEnum.BATHPATH);


    private String fileType;

    private String path;

    private static final String BATHPATH = String.format("C:\\Users\\%s\\Desktop\\torrent\\%s", System.getenv().get("USERNAME"), DateTime.now().toString("yyyyMMdd"));

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
