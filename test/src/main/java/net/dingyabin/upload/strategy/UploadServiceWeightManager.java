package net.dingyabin.upload.strategy;

import net.dingyabin.upload.source.IUploadService;
import net.dingyabin.upload.model.UploadServiceWeight;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:22:35
 */
@Service
public class UploadServiceWeightManager implements InitializingBean {

    private List<IUploadService> uploadServices;


    private UploadServiceWeight[] uploadServiceWeights;


    private final ConcurrentHashMap<String, IUploadService> serviceMap = new ConcurrentHashMap<>();


    @Override
    public void afterPropertiesSet() {
        this.uploadServiceWeights = new UploadServiceWeight[uploadServices.size()];
        for (int i = 0; i < uploadServices.size(); i++) {
            IUploadService iUploadService = uploadServices.get(i);
            uploadServiceWeights[i] = new UploadServiceWeight(iUploadService.uploadSource());
            serviceMap.put(iUploadService.uploadSource(), iUploadService);
        }
    }




    public IUploadService selectBestUploadService(List<IUploadService> uploadServices){
        Optional<UploadServiceWeight> bestService = Arrays.stream(uploadServiceWeights).max(Comparator.comparingInt(k -> k.getAtomicInteger().get()));
        return bestService.map(weight -> serviceMap.get(weight.getUploadSource())).orElse(null);
    }




    public void addUploadServiceWeight(String uploadSource) {
        Arrays.stream(uploadServiceWeights)
                .filter(w -> w.getUploadSource().equals(uploadSource))
                .forEach(w -> w.getAtomicInteger().incrementAndGet());
    }



    public void decreUploadServiceWeight(String uploadSource) {
        Arrays.stream(uploadServiceWeights)
                .filter(w -> w.getUploadSource().equals(uploadSource))
                .forEach(w -> w.getAtomicInteger().decrementAndGet());
    }


    @Autowired
    public void setUploadServices(List<IUploadService> uploadServices) {
        this.uploadServices = uploadServices;
    }
}
