package net.dingyabin.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2020/10/24.
 * Time:5:23
 */
@Data
public class FileResult {

    private String movieName;

    private String moviePath;

    private List<String> parts = new ArrayList<>();


    public FileResult addPart(String part){
        parts.add(part);
        return this;
    }





}
