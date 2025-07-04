package com.eb.config.base.properties.nested;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 这里除了这些配置之外，还需要配置两个环境变量：OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET
 *
 * @author suyh
 * @since 2025-04-15
 */
@Data
public class FileOssProperties {
    @NotBlank
    private String region;
    @NotBlank
    private String endpoint;
    @NotBlank
    private String bucketName;
}
