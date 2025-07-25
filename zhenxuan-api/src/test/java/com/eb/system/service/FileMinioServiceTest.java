package com.eb.system.service;

import com.eb.ZhenXuanApplication;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author suyh
 * @since 2025-07-25
 */
@ActiveProfiles("suyh_mac")
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = ZhenXuanApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class FileMinioServiceTest {
    @Resource
    private FileMinioService fileMinioService;

    // RbsJtHjvUT112i0TJjUn
    // zd8Oy2ej0V4nBWwkN8qDSSOBIiq4V7vqjEtsnEDn
    private static final String ak = "M4HGteCOYWk43AdyKlXN";
    private static final String sk = "JZnytJbJWQGyr0u05zJsRRc3VQqCDCGN6HAn4goJ";

    @Test
    public void bucketTest() {
        // 初始化客户端
        MinioClient minioClient = MinioClient.builder()
//                .endpoint("http://minio.qu-yun.isuyh.com") // MinIO 服务地址（HTTP/HTTPS）
//                .endpoint("http://211.101.244.187:9000") // MinIO 服务地址（HTTP/HTTPS）
                .endpoint("http://qu-yun.isuyh.com:9000") // MinIO 服务地址（HTTP/HTTPS）
                .credentials(ak, sk) // 访问密钥和密钥
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
        } catch (MinioException e) {
            System.err.println("失败：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void existBucketTest() {
        boolean flag = fileMinioService.foundBucket("test");
        Assertions.assertTrue(flag);
    }
}