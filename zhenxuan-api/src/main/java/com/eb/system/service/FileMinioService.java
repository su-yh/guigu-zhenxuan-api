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
import org.springframework.util.StringUtils;

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

            MinioClient.Builder builder = MinioClient.builder();
            builder.endpoint(minioProperties.getEndpoint());
            builder.credentials(minioProperties.getAk(), minioProperties.getSk());
            if (StringUtils.hasText(minioProperties.getRegion())) {
                builder.region(minioProperties.getRegion());
            }
            minioClient = builder.build();
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
            MakeBucketArgs build = MakeBucketArgs.builder().bucket(bucketName).build();
            obtainMinioClient().makeBucket(build);
            return true;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException |
                 InvalidKeyException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean foundBucket(@NonNull String bucketName) {

        try {
            return obtainMinioClient().bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (MinioException e) {
            System.err.println("失败：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
