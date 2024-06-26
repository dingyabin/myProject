package net.dingyabin.upload.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:11
 */
@Getter
@Setter
public class ImageUplaodContext<T> {

    private String uploadSource;

    private UploadResult uploadResult;

    private T uploadParam;


    public ImageUplaodContext() {
    }

    public ImageUplaodContext(String uploadSource) {
        this.uploadSource = uploadSource;
    }

}
