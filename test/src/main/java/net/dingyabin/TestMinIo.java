package net.dingyabin;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 丁亚宾
 * Date: 2024/7/10.
 * Time:20:19
 */
public class TestMinIo {



    public static void main(String[] args) {
        MinioClient minioClient = MinioClient.builder().endpoint("http://192.168.118.132", 9000, false)
                .credentials("dingyabin", "12345678")
                .build();
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            for (Bucket bucket : buckets) {
                System.out.println("Bucket: " + bucket.name() + ", Creation Date: " + bucket.creationDate());
            }

//            ObjectWriteResponse test = minioClient.uploadObject(
//                    UploadObjectArgs.builder()
//                            .bucket("test")
//                            .object(UUID.randomUUID().toString() + ".jpg")
//                            .filename("F:\\思维导图\\5aab34100001a9cd19781256.jpg")
//                            .build()
//            );


//          String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
//                    .method(Method.GET)
//                    .bucket("test")
//                    .object("1aea2afa-ec30-4301-96c7-7bd4b0a397e1.jpg")
//                    .expiry(1, TimeUnit.HOURS)//按小时传参数
//                    .build());

//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket("test")
//                            .object(UUID.randomUUID().toString() + ".jpg")
//                            .stream(new FileInputStream("F:\\思维导图\\5aab34100001a9cd19781256.jpg"), -1 , 5*1024*1024)
//                            .build());

            System.out.println("cxxxxxx  " );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
