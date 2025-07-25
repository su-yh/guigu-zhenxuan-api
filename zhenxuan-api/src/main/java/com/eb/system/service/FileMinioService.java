package com.eb.system.service;

import com.eb.config.base.properties.BaseProperties;
import com.eb.config.base.properties.nested.FileMinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author suyh
 * @since 2025-07-25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileMinioService {
    private final BaseProperties properties;

    private volatile MinioClient minioClient;

    @NonNull
    protected MinioClient obtainMinioClient() {
        if (minioClient != null) {
            return minioClient;
        }

        synchronized (MinioClient.class) {
            if (minioClient != null) {
                return minioClient;
            }

            FileMinioProperties minioProperties = properties.getFile().getMinio();

            minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpoint())
                    .credentials(minioProperties.getAk(), minioProperties.getSk())
                    .build();
        }

        return minioClient;
    }

    @PreDestroy
    public void destroy() {
        if (minioClient != null) {
            try {
                minioClient.close();
            } catch (Exception e) {
                log.warn("minio client failed.", e);
            }
        }
    }

    public boolean makeBucket(@NonNull String bucketName) {
        try {
            MinioClient minioClient = obtainMinioClient();

            MakeBucketArgs build = MakeBucketArgs.builder().bucket(bucketName).build();
            minioClient.makeBucket(build);
            return true;
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean foundBucket(@NonNull String bucketName) {

        FileMinioProperties minioProperties = properties.getFile().getMinio();
        // 初始化客户端
        MinioClient minioClient = MinioClient.builder()
//                .endpoint("http://minio.qu-yun.isuyh.com") // MinIO 服务地址（HTTP/HTTPS）
//                .endpoint("http://211.101.244.187:9000") // MinIO 服务地址（HTTP/HTTPS）
                .endpoint("http://qu-yun.isuyh.com:9000") // MinIO 服务地址（HTTP/HTTPS）
                .credentials(minioProperties.getAk(), minioProperties.getSk()) // 访问密钥和密钥
                .build();

        // 验证连接（可选）
        try {
//            String bucketName = "test";
//            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("test").build());

            System.out.println("exists: " + found);
//            boolean isConnected = minioClient.bucketExists(bucketExistsArgs -> bucketExistsArgs.bucket("test-bucket"));
//            System.out.println("连接成功：" + isConnected);
            return found;
        } catch (MinioException e) {
            System.err.println("失败：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
