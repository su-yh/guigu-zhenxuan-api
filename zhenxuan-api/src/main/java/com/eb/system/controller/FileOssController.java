package com.eb.system.controller;

import com.base.web.response.dto.R;
import com.eb.system.service.FileOssService;
import com.web.sys.authentication.annotation.CurrLoginUser;
import com.web.sys.authentication.user.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author suyh
 * @since 2025-04-15
 */
@Tag(name = "【OSS】文件系统")
@RestController
@RequestMapping("/system/file/oss")
@RequiredArgsConstructor
@Validated
@Slf4j
public class FileOssController {
    private final FileOssService fileOssService;

    @Operation(summary = "文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public R<String> uploadFiles(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser,
            @RequestParam(value = "file") MultipartFile file) {
        String path = fileOssService.uploadFile(file, loginUser);
        return R.ofSuccess(path);
    }
}
