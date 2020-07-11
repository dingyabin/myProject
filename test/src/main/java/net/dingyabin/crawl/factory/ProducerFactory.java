package net.dingyabin.crawl.factory;

import net.dingyabin.crawl.enums.WebSiteEnum;
import net.dingyabin.crawl.model.Torrent;
import net.dingyabin.crawl.producer.*;

import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2018/7/29.
 * Time:0:47
 *
 * @author MrDing
 */
public class ProducerFactory {

    public static AbstractTorrentProducer getProducer(WebSiteEnum webSiteEnum, BlockingQueue<Torrent> queue, int page) {
        switch (webSiteEnum) {
            case DYHS:
                return new DYHSTorrentProducer(queue, "gbk", page);
            case DYTT:
                return new DYTTorrentProducer(queue, "gb2312", page);
            case E048:
                return new E048TorrentProducer(queue, "utf-8", page);
            case ASMR:
                return new AMSRTorrentProducer(queue, "utf-8", page);
            case TWO66NA:
                return new Two66naTorrentProducer(queue, "utf-8", page);
            case LPXXS:
                return new LpxxsTorrentProducer(queue, "utf-8", page);
            case M3U8:
                return new M3U8Producer(queue, "utf-8", page);
            default:
                throw new IllegalArgumentException("暂不支持此网站的解析...");
        }
    }
}
