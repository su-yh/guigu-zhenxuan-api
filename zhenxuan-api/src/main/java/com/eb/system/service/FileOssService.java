package com.eb.system.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.eb.config.base.properties.BaseProperties;
import com.eb.config.base.properties.nested.FileOssProperties;
import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.UUID;

/**
 * @author suyh
 * @since 2025-04-15
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileOssService {
    private final BaseProperties baseProperties;
    private OSS ossClient;

    @PostConstruct
    public void init() throws Exception {
        // 从环境变量中获取访问凭证。运行本代码之前，请先配置环境变量
        // OSS_ACCESS_KEY_ID=
        // OSS_ACCESS_KEY_SECRET=
        EnvironmentVariableCredentialsProvider credentialsProvider
                = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 这个到底有没有用，只是创建但没有地方调用。
//        // 创建 OSSClient 实例
//        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
//        // 显式声明使用 V4 签名算法
//        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        FileOssProperties ossProperties = baseProperties.getFile().getOss();
        ossClient = OSSClientBuilder.create()
                .endpoint(ossProperties.getEndpoint())
                .credentialsProvider(credentialsProvider)
                .region(ossProperties.getRegion())
                .build();
    }

    @PreDestroy
    public void destroy() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    public String uploadFile(MultipartFile file, LoginUser loginUser) {
        try {
            DataSize dataSize = DataSize.ofBytes(file.getSize());
            long mb = dataSize.toMegabytes();
            if (mb > 5) {
                throw ExceptionUtil.business(ErrorCodeConstants.FILE_TOO_LARGE);
            }

            String bucketName = baseProperties.getFile().getOss().getBucketName();
            String username = loginUser.getUsername();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String key = String.format("%s/%s-%s", username, uuid, file.getOriginalFilename());
            if (true) {
                // 文件上传方式一
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream());

                // 指定上传文件操作时是否覆盖同名Object。
                // 不指定x-oss-forbid-overwrite时，默认覆盖同名Object(文件)。
                // 指定x-oss-forbid-overwrite为false时，表示允许覆盖同名Object。
                // 指定x-oss-forbid-overwrite为true时，表示禁止覆盖同名Object，如果同名Object已存在，程序将报错。
                putObjectRequest.addHeader("x-oss-forbid-overwrite", "true");
                ossClient.putObject(putObjectRequest);
            } else {
                // 文件上传方式二
                ossClient.putObject(bucketName, key, file.getInputStream());
            }
            return "https://isuyh.com/suyh/static/" + key;
        } catch (ClientException | IOException ce) {
            log.error("OSS: uploadFile failed");
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }
    }
}
