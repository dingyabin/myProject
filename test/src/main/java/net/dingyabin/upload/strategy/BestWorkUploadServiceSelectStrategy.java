package net.dingyabin.upload.strategy;

import net.dingyabin.upload.model.ImageUplaodContext;
import net.dingyabin.upload.model.UploadResult;
import net.dingyabin.upload.source.AbstractUploadService;
import net.dingyabin.upload.source.IUploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:45
 */
@Service
public class BestWorkUploadServiceSelectStrategy extends UploadServiceSelectStrategy {


    @Resource
    private UploadServiceWeightManager uploadServiceWeightManager;


    private Map<String, IUploadService> serviceMap = new ConcurrentHashMap<>();


    @Override
    public IUploadService selectServiceSelectStrategy(List<IUploadService> uploadServices) {
        IUploadService iUploadService = uploadServiceWeightManager.selectBestUploadService(uploadServices);
        if (iUploadService instanceof AbstractUploadService) {
            return serviceMap.computeIfAbsent(iUploadService.uploadSource(), uploadSource -> new UploadServiceWrapper((AbstractUploadService) iUploadService));
        }
        return iUploadService;
    }



    private class UploadServiceWrapper extends AbstractUploadService {

        private AbstractUploadService abstractUploadService;

        public UploadServiceWrapper(AbstractUploadService abstractUploadService) {
            this.abstractUploadService = abstractUploadService;
        }

        @Override
        public ImageUplaodContext initImageUplaodContext() {
            return abstractUploadService.initImageUplaodContext();
        }

        @Override
        public void beforeUpload(ImageUplaodContext imageUplaodContext) {
            abstractUploadService.beforeUpload(imageUplaodContext);
        }

        @Override
        public void afterUpload(ImageUplaodContext imageUplaodContext) {
            abstractUploadService.afterUpload(imageUplaodContext);
            UploadResult uploadResult = imageUplaodContext.getUploadResult();
            try {
                if (uploadResult.isSuccess()) {
                    uploadServiceWeightManager.addUploadServiceWeight(imageUplaodContext.getUploadSource());
                } else {
                    uploadServiceWeightManager.decreUploadServiceWeight(imageUplaodContext.getUploadSource());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        @Override
        public UploadResult doUpload(File file, ImageUplaodContext imageUplaodContext) {
            return abstractUploadService.doUpload(file, imageUplaodContext);
        }



        @Override
        public String uploadSource() {
            return abstractUploadService.uploadSource();
        }
    }


}
