package com.eb.system.service;

import com.eb.config.base.properties.BaseProperties;
import com.eb.config.base.properties.nested.FileMinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 最后生成的文件 路径是：http://minio.qu-yun.isuyh.com/{bucketName}/filePath
 *
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
                 InvalidKeyException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
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

    public void listObjects(@NonNull String bucketName, @Nullable String pathPrefix) {
        // 列举存储桶中所有对象（支持分页、前缀过滤）
        ListObjectsArgs.Builder builder = ListObjectsArgs.builder();
        builder.bucket(bucketName);
        if (StringUtils.hasText(pathPrefix)) {
            builder.prefix(pathPrefix); // 只列举前缀的对象
        }
        builder.recursive(false); // 是否递归子目录，关闭递归时，配置前缀只查询指定目录下的文件
        ListObjectsArgs args = builder.build();
        Iterable<Result<Item>> results = obtainMinioClient().listObjects(args);

        try {
            for (Result<Item> result : results) {
                Item item = result.get();

                // 是否目录
                if (item.isDir()) {
                    log.info("目录: {}", item.objectName());
                } else {
                    log.info("文件: {}", item.objectName());
                }
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }
}
