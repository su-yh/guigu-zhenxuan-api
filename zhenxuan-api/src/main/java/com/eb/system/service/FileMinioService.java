package com.eb.system.service;

import com.eb.config.base.properties.BaseProperties;
import com.eb.config.base.properties.nested.FileMinioProperties;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
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
}
