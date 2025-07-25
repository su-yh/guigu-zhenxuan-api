package com.eb.system.controller;

import com.eb.system.service.FileMinioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suyh
 * @since 2025-07-25
 */
@Tag(name = "【OSS】文件系统")
@RestController
@RequestMapping("/system/file/minio")
@RequiredArgsConstructor
@Validated
@Slf4j
public class FileMinioController {
    private final FileMinioService fileMinioService;


}
