package com.eb.system.service;

import com.eb.ZhenXuanApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

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

    @Test
    public void existBucketTest() {
        boolean flag = fileMinioService.foundBucket("test");
        Assertions.assertTrue(flag);
    }

    @Test
    public void makeBucketTest() {
        boolean flag = fileMinioService.makeBucket("test2");
        Assertions.assertTrue(flag);
    }

    @Test
    public void listObjectsTest() {
        fileMinioService.listObjects("test", "dir1/");
    }
}