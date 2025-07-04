package com.eb.config.base.properties.nested;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.validation.Valid;

/**
 * @author suyh
 * @since 2025-04-15
 */
@Data
public class FileProperties {
    // fileUpload
    @NestedConfigurationProperty
    @Valid
    private final FileLocalProperties local = new FileLocalProperties();

    @NestedConfigurationProperty
    @Valid
    private final FileOssProperties oss = new FileOssProperties();

}
