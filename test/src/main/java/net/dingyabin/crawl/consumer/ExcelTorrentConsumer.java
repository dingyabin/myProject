package net.dingyabin.crawl.consumer;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import net.dingyabin.bean.ExcelBean;
import net.dingyabin.crawl.enums.WebSiteEnum;
import net.dingyabin.crawl.model.Torrent;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;

/**
 * @author 丁亚宾
 * Date: 2023/3/26.
 * Time:23:10
 */
public class ExcelTorrentConsumer extends SimpleTorrentConcumer {

    private ExcelWriter excelWriter;

    private WriteSheet writeSheet;

    private Class<? extends ExcelBean> excelBeanClass;


    public ExcelTorrentConsumer(Class<? extends ExcelBean> excelBeanClass) {
        this.excelBeanClass = excelBeanClass;
        this.excelWriter = EasyExcel.write("C:\\Users\\Administrator\\Desktop\\torrent\\20240103\\soft.xlsx", excelBeanClass).build();
        this.writeSheet = EasyExcel.writerSheet().build();
    }


    public ExcelTorrentConsumer(Class<? extends ExcelBean> excelBeanClass, WebSiteEnum webSiteEnum, BlockingQueue<Torrent> queue) {
        super(webSiteEnum, queue);
        this.excelBeanClass = excelBeanClass;
    }


    @Override
    protected void doWrite(Torrent torrent, File file, byte[] bytes) throws IOException {
        try {
            ExcelBean excelBean = excelBeanClass.newInstance().build(new String(bytes));
            excelWriter.write(Collections.singleton(excelBean), writeSheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doInFinally() {
        excelWriter.close();
    }



    @Override
    protected int getWaitTimeSec() {
        return 5;
    }
}
