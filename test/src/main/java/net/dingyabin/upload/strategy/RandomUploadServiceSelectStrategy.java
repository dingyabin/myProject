package net.dingyabin.upload.strategy;

import net.dingyabin.upload.source.IUploadService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:31
 */
@Service
public class RandomUploadServiceSelectStrategy extends UploadServiceSelectStrategy {

    @Override
    public IUploadService selectServiceSelectStrategy(List<IUploadService> uploadServices) {
        return uploadServices.get(RandomUtils.nextInt(0, uploadServices.size()));
    }
}
