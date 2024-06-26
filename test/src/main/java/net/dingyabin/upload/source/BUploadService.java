package net.dingyabin.upload.source;

import net.dingyabin.upload.model.BUploadParam;
import net.dingyabin.upload.model.ImageUplaodContext;
import net.dingyabin.upload.model.UploadResult;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:20:58
 */
@Service
public class BUploadService extends AbstractUploadService<BUploadParam> {

    private final String UPLOAD_SOURCE = "B";

    @Override
    public String uploadSource() {
        return UPLOAD_SOURCE;
    }


    @Override
    public ImageUplaodContext<BUploadParam> initImageUplaodContext() {
        ImageUplaodContext<BUploadParam> imageUplaodContext = new ImageUplaodContext<>(UPLOAD_SOURCE);
        imageUplaodContext.setUploadParam(new BUploadParam());
        return imageUplaodContext;
    }


    @Override
    public void beforeUpload(ImageUplaodContext<BUploadParam> imageUplaodContext) {

    }

    @Override
    public void afterUpload(ImageUplaodContext<BUploadParam> imageUplaodContext) {

    }

    @Override
    public UploadResult doUpload(File file, ImageUplaodContext<BUploadParam> imageUplaodContext) {
        BUploadParam uploadParam = imageUplaodContext.getUploadParam();
        System.out.println("BUploadService 开始上传......");
        return null;
    }


}
