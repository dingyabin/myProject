package net.dingyabin.upload.strategy;

import net.dingyabin.upload.source.IUploadService;

import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2024/6/26.
 * Time:21:30
 */
public abstract class UploadServiceSelectStrategy {


    public abstract IUploadService selectServiceSelectStrategy(List<IUploadService> uploadServices);


}
