package com.eb.config.base.properties.nested;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author suyh
 * @since 2025-07-25
 */
@Data
public class FileMinioProperties {
    private String region = "";
    @NotBlank
    private String endpoint;
    @NotBlank
    private String ak;
    @NotBlank
    private String sk;
}
