package net.dingyabin.upload.source;

import net.dingyabin.upload.model.AUploadParam;
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
public class AUploadService extends AbstractUploadService<AUploadParam> {

    private final String UPLOAD_SOURCE = "A";

    @Override
    public String uploadSource() {
        return UPLOAD_SOURCE;
    }


    @Override
    public ImageUplaodContext<AUploadParam> initImageUplaodContext() {
        ImageUplaodContext<AUploadParam> imageUplaodContext = new ImageUplaodContext<>(UPLOAD_SOURCE);
        imageUplaodContext.setUploadParam(new AUploadParam());
        return imageUplaodContext;
    }


    @Override
    public void beforeUpload(ImageUplaodContext<AUploadParam> imageUplaodContext) {

    }

    @Override
    public void afterUpload(ImageUplaodContext<AUploadParam> imageUplaodContext) {

    }

    @Override
    public UploadResult doUpload(File file, ImageUplaodContext<AUploadParam> imageUplaodContext) {
        AUploadParam uploadParam = imageUplaodContext.getUploadParam();
        System.out.println("AUploadService 开始上传......");
        return null;
    }


}
