package net.dingyabin.upload.strategy;

import net.dingyabin.upload.model.ImageUplaodContext;
import net.dingyabin.upload.model.ResultRecord;
import net.dingyabin.upload.model.UploadResult;
import net.dingyabin.upload.source.AbstractUploadService;
import net.dingyabin.upload.source.IUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:45
 */
@Service
public class BestWorkUploadServiceSelectStrategy extends UploadServiceSelectStrategy {

    @Value("${servicePerfermence.maxCount:50}")
    private int maxCount;

    private final Map<String, UploadServiceProxy> serviceMap = new ConcurrentHashMap<>();


    @Override
    public IUploadService selectServiceSelectStrategy(List<IUploadService> uploadServices) {
        List<UploadServiceProxy> collect = uploadServices.stream()
                .map(service -> {
                    return serviceMap.computeIfAbsent(service.uploadSource(),
                            k -> new UploadServiceProxy((AbstractUploadService) service, new ResultRecord(maxCount)));
                }).collect(Collectors.toList());
        Optional<UploadServiceProxy> max = collect.stream().max(Comparator.comparingInt(UploadServiceProxy::successCount));
        return max.orElse(null);
    }



    private class UploadServiceProxy extends AbstractUploadService {

        private AbstractUploadService abstractUploadService;

        private ResultRecord resultRecord;

        public UploadServiceProxy(AbstractUploadService abstractUploadService, ResultRecord resultRecord) {
            this.abstractUploadService = abstractUploadService;
            this.resultRecord = resultRecord;
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
            //记录结果
            resultRecord.setResult(uploadResult.isSuccess());
        }


        @Override
        public UploadResult doUpload(File file, ImageUplaodContext imageUplaodContext) {
            return abstractUploadService.doUpload(file, imageUplaodContext);
        }


        @Override
        public String uploadSource() {
            return abstractUploadService.uploadSource();
        }


        public ResultRecord getResultRecord(){
            return this.resultRecord;
        }


        public int successCount(){
            return getResultRecord().successCount();
        }
    }


}
