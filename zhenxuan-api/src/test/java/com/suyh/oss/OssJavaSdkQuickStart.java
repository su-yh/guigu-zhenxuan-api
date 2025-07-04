package com.suyh.oss;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OssJavaSdkQuickStart {
    public static void main(String[] args) throws com.aliyuncs.exceptions.ClientException {
        // 设置 OSS Endpoint 和 Bucket 名称
        // 公网访问
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        // 内网访问
        String endpointInternal = "https://oss-cn-shenzhen-internal.aliyuncs.com";
        endpoint = endpointInternal;
        String bucketName = "suyh-shenzhen-public";
        // 替换为您的 Bucket 区域
        String region = "cn-shenzhen";

        String accessKeyId = "accessKeyId";
        String accessKeySecret = "accessKeySecret";

        // 从环境变量中获取访问凭证。运行本代码示例之前，请先配置环境变量
        // OSS_ACCESS_KEY_ID=
        // OSS_ACCESS_KEY_SECRET=
//        EnvironmentVariableCredentialsProvider credentialsProvider =
//                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);

        // 创建 OSSClient 实例
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用 V4 签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .region(region)
                .build();
        try {
            // 1. 创建存储空间（Bucket）
//            ossClient.createBucket(bucketName);
//            System.out.println("1. Bucket " + bucketName + " 创建成功。");
            {
                // 2. 上传文件
                String objectName = "suyh-a/exampleobject.txt";
                String content = "Hello OSS";
                ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
                System.out.println("2. 文件 " + objectName + " 上传成功。");
            }

            {
                String objectName = "suyh-a/avatar.jpg";
                // 3. 下载文件
                OSSObject ossObject = ossClient.getObject(bucketName, objectName);
                InputStream contentStream = ossObject.getObjectContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(contentStream));
                String line;
                System.out.println("3. 下载的文件内容：");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                contentStream.close();
            }

            {
                // 4. 列出文件
                System.out.println("4. 列出 Bucket 中的文件：");
                ObjectListing objectListing = ossClient.listObjects(bucketName);
                for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    System.out.println(" - " + objectSummary.getKey() + " (大小 = " + objectSummary.getSize() + ")");
                }
            }

            {
                // 5. 删除文件
                String objectName = "suyh-a/avatar.jpg";
                ossClient.deleteObject(bucketName, objectName);
                System.out.println("5. 文件 " + objectName + " 删除成功。");
            }


//            {
//                // 6. 删除存储空间（Bucket）
//                ossClient.deleteBucket(bucketName);
//                System.out.println("6. Bucket " + bucketName + " 删除成功。");
//            }
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException | IOException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
