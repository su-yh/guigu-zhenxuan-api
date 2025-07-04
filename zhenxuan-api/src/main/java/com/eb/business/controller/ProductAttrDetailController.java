package com.eb.business.controller;

import com.base.web.validation.groups.ValidationGroups;
import com.eb.business.dto.product.attr.ProductAttrDetailDto;
import com.eb.business.service.ProductAttrDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;
import java.util.List;

@Tag(name = "商品属性详情")
@RestController
@RequestMapping("/product/attr/detail")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ProductAttrDetailController {
    private final ProductAttrDetailService productAttrDetailService;

    @Operation(summary = "【商品属性详情】查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ProductAttrDetailDto> listByCategory(@RequestParam Long categoryId) {
        return productAttrDetailService.listByCategory(categoryId);
    }

    @Operation(summary = "【商品属性详情】创建")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@Validated({ValidationGroups.Req.Create.class, Default.class}) @RequestBody ProductAttrDetailDto dto) {
        productAttrDetailService.create(dto);
    }

    @Operation(summary = "【商品属性详情】更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@Validated({ValidationGroups.Req.Update.class, Default.class}) @RequestBody ProductAttrDetailDto dto) {
        productAttrDetailService.update(dto);
    }

    @Operation(summary = "【商品属性详情】删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam Long nameId) {
        productAttrDetailService.delete(nameId);
    }


}
