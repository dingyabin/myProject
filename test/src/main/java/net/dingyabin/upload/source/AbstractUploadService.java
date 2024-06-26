package net.dingyabin.upload.source;

import net.dingyabin.upload.model.ImageUplaodContext;
import net.dingyabin.upload.model.UploadResult;

import java.io.File;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:02
 */
public abstract class AbstractUploadService<T> implements IUploadService {


    @Override
    public UploadResult upload(File file) {
        ImageUplaodContext<T> imageUplaodContext = initImageUplaodContext();
        beforeUpload(imageUplaodContext);
        UploadResult uploadResult = doUpload(file, imageUplaodContext);
        imageUplaodContext.setUploadResult(uploadResult);
        afterUpload(imageUplaodContext);
        return uploadResult;
    }


    public abstract ImageUplaodContext<T> initImageUplaodContext();


    public abstract void beforeUpload(ImageUplaodContext<T> imageUplaodContext);


    public abstract void afterUpload(ImageUplaodContext<T> imageUplaodContext);


    public abstract UploadResult doUpload(File file, ImageUplaodContext<T> imageUplaodContext);

}
