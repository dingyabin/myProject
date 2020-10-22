package net.dingyabin.crawl.producer;

import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:23:57
 */
public class CaiTaZhiJiaProducer extends AbstractTorrentProducer {

    private String url = "http://888.yage360.com/gm/81/%s.mp4";

    private String parentPath;

    public static AtomicInteger coiunt = new AtomicInteger(0);


    public CaiTaZhiJiaProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
        this.url = String.format(this.url, pageNumber);
        this.parentPath = url.substring(0, url.lastIndexOf("/"));
    }


    @Override
    protected String getUrl() {
        return this.url;
    }


    @Override
    protected int getConnTimeOut() {
        return 60000;
    }

    @Override
    public void run() {
        try {
            byte[] fileResource = getFileResource(getUrl());
            if(fileResource == null || fileResource.length ==0){
                System.out.println("xxxxxx失败 ，page =" + getPageNumber());
                coiunt.incrementAndGet();
                return;
            }
            System.out.println("获取一个 ，url =" + getUrl());
            pushTorrent(new Torrent(getPageNumber() + "", null, fileResource));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





    @Override
    protected List<Torrent> makeTorrent(String resource) {
        return null;
    }

}
