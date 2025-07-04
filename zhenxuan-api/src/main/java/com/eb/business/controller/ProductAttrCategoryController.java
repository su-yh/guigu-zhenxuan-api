package com.eb.business.controller;

import com.eb.business.service.ProductAttrCategoryService;
import com.eb.mp.mysql.entity.business.ProductAttrCategoryEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author suyh
 * @since 2025-04-17
 */
@Tag(name = "商品属性分类")
@RestController
@RequestMapping("/product/attr/category")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ProductAttrCategoryController {
    private final ProductAttrCategoryService productAttrCategoryService;

    @Operation(summary = "【商品属性分类】查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ProductAttrCategoryEntity> listByParentId(@RequestParam(required = false, defaultValue = "0") Long parentId) {
        return productAttrCategoryService.listByParentId(parentId);
    }
}
