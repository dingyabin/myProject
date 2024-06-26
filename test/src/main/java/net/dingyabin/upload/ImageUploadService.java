package net.dingyabin.upload;

import net.dingyabin.upload.model.UploadResult;
import net.dingyabin.upload.source.IUploadService;
import net.dingyabin.upload.strategy.UploadServiceSelectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:22:53
 */
@Service
public class ImageUploadService {

    @Value("${imageUpload.SelectStrategy}")
    private String selectStrategy;


    private List<IUploadService> uploadServices;


    @Resource
    private UploadServiceSelectStrategy randomUploadServiceSelectStrategy;




    public UploadResult uploadImage(File file) {
        IUploadService iUploadService = randomUploadServiceSelectStrategy.selectServiceSelectStrategy(uploadServices);
        if (iUploadService == null) {
            throw new RuntimeException("没有合适的上传服务....");
        }
        return iUploadService.upload(file);
    }



    @Autowired
    public void setUploadServices(List<IUploadService> uploadServices) {
        this.uploadServices = uploadServices;
    }
}
