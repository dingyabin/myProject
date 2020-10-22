package net.dingyabin.crawl.model;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:27
 */
public class Torrent {

    private String name;
    private String url;
    private byte[] content;

    public Torrent() {
    }

    public Torrent(String name, String url) {
        this(name, url, null);
    }

    public Torrent(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public Torrent(String name, String url, byte[] content) {
        this.name = name;
        this.url = url;
        this.content = content;
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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
