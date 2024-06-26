package net.dingyabin.upload.source;

import net.dingyabin.upload.model.UploadResult;

import java.io.File;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:20:54
 */
public interface IUploadService {

    String uploadSource();

    UploadResult upload(File file);

}
