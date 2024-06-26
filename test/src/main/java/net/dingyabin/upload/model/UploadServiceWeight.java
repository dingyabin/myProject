package net.dingyabin.upload.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:53
 */
@Getter
@Setter
public class UploadServiceWeight {

    private String uploadSource;

    private AtomicInteger atomicInteger;

    public UploadServiceWeight(String uploadSource) {
        this.uploadSource = uploadSource;
        this.atomicInteger = new AtomicInteger(Integer.MAX_VALUE / 2);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UploadServiceWeight that = (UploadServiceWeight) o;
        return Objects.equals(uploadSource, that.uploadSource);
    }


    @Override
    public int hashCode() {
        return Objects.hash(uploadSource);
    }
}
