package net.dingyabin.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 丁亚宾
 * Date: 2023/3/26.
 * Time:23:24
 */
@Getter
@Setter
@AllArgsConstructor
public class TorrentDownload implements ExcelBean {

    @ExcelProperty(value = "名字", index = 0)
    private String name;

    @ExcelProperty(value = "磁力", index = 1)
    private String magnet;

    public TorrentDownload() {
    }




    @Override
    public ExcelBean build(String context) {
        JSONObject jsonObject = JSONObject.parseObject(context);
        this.name = jsonObject.getString("name");
        this.magnet = jsonObject.getString("magnet");
        return this;
    }
}
