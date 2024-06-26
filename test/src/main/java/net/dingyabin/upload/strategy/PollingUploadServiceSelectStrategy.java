package net.dingyabin.upload.strategy;

import net.dingyabin.upload.source.IUploadService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:33
 */
@Service
public class PollingUploadServiceSelectStrategy extends UploadServiceSelectStrategy {

    private final AtomicInteger INDEX = new AtomicInteger(0);

    @Override
    public IUploadService selectServiceSelectStrategy(List<IUploadService> uploadServices) {
        int nextIndex = Math.abs(INDEX.getAndIncrement()) % uploadServices.size();
        return uploadServices.get(nextIndex);
    }

}
