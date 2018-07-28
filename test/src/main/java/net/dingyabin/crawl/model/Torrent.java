package net.dingyabin.crawl.model;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:27
 */
public class Torrent {

    private String name;
    private String url;

    public Torrent() {
    }

    public Torrent(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
