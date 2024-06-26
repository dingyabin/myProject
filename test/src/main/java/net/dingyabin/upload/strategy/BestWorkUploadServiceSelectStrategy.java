package net.dingyabin.upload.strategy;

import net.dingyabin.upload.model.ImageUplaodContext;
import net.dingyabin.upload.model.UploadResult;
import net.dingyabin.upload.model.UploadServicePerfermence;
import net.dingyabin.upload.source.AbstractUploadService;
import net.dingyabin.upload.source.IUploadService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:45
 */
@Service
public class BestWorkUploadServiceSelectStrategy extends UploadServiceSelectStrategy implements InitializingBean {

    @Value("${servicePerfermence.maxCount:50}")
    private int maxCount;

    @Autowired
    private List<IUploadService> uploadServices;

    private Map<String, UploadServiceWrapper> serviceMap = new ConcurrentHashMap<>();

    private Map<String, UploadServicePerfermence> servicePerfermenceMap = new ConcurrentHashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        for (IUploadService uploadService : uploadServices) {
            servicePerfermenceMap.put(uploadService.uploadSource(), new UploadServicePerfermence(maxCount));
        }
    }


    @Override
    public IUploadService selectServiceSelectStrategy(List<IUploadService> uploadServices) {
        Optional<Map.Entry<String, UploadServicePerfermence>> max = servicePerfermenceMap.entrySet().stream().max(Comparator.comparingInt(e -> e.getValue().successCount()));
        String uploadSource = max.map(e -> e.getKey()).orElse(null);

        Optional<IUploadService> iUploadServiceOptional = uploadServices.stream().filter(e -> e.uploadSource().equals(uploadSource)).findFirst();
        IUploadService iUploadService = iUploadServiceOptional.orElse(null);

        if (iUploadService == null) {
            return null;
        }

        if (iUploadService instanceof AbstractUploadService) {
            return serviceMap.computeIfAbsent(iUploadService.uploadSource(), k -> new UploadServiceWrapper((AbstractUploadService) iUploadService));
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
                UploadServicePerfermence uploadServicePerfermence = servicePerfermenceMap.get(imageUplaodContext.getUploadSource());
                if (uploadServicePerfermence != null) {
                    uploadServicePerfermence.setResult(uploadResult.isSuccess());
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
