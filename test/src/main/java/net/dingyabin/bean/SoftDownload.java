package net.dingyabin.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 丁亚宾
 * Date: 2023/3/26.
 * Time:23:24
 */
@Getter
@Setter
public class SoftDownload implements ExcelBean {

    @ExcelProperty(value = "软件名", index = 0)
    private String name;

    @ExcelProperty(value = "地址", index = 1)
    private String url;

    @ExcelProperty(value = "提取码", index = 2)
    private String code;

    @ExcelProperty(value = "操作方法", index = 3)
    private String operation;

    @ExcelProperty(value = "图例", index = 4)
    private String imgs;

    public SoftDownload() {
    }



    @Override
    public ExcelBean build(String context) {
        JSONObject jsonObject = JSONObject.parseObject(context);
        this.name = jsonObject.getString("name");
        this.url= jsonObject.getString("url");
        this.code= jsonObject.getString("code");
        this.operation= jsonObject.getString("operation");
        this.imgs= jsonObject.getString("imgs");
        return this;
    }
}
