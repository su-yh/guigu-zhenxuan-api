package com.eb.business.controller;

import com.base.mp.mybatis.PageParam;
import com.base.mp.mybatis.PageResult;
import com.base.web.validation.groups.ValidationGroups;
import com.eb.business.service.ProductTrademarkService;
import com.eb.mp.mysql.entity.business.ProductTrademarkEntity;
import com.web.sys.dto.base.IdBody;
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

@Tag(name = "品牌管理")
@RestController
@RequestMapping("/product/trademark")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ProductTrademarkController {
    private final ProductTrademarkService productTrademarkService;


    @Operation(summary = "【品牌管理】查询(分页)")
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public PageResult<ProductTrademarkEntity> listPage(
            PageParam pageParam, ProductTrademarkEntity queryEntity) {
        return productTrademarkService.listPage(pageParam, queryEntity);
    }

    @Operation(summary = "【品牌管理】查询 by id")
    @RequestMapping(value = "/query/byId", method = RequestMethod.GET)
    public ProductTrademarkEntity queryById(
            @RequestParam("id") Long id) {
        return productTrademarkService.queryById(id);
    }

    @Operation(summary = "【品牌管理】创建")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(
            @RequestBody @Validated({ValidationGroups.Req.Create.class, Default.class}) ProductTrademarkEntity entity) {
        productTrademarkService.create(entity);
    }

    @Operation(summary = "【品牌管理】更新")
    @RequestMapping(value = "/update/byId", method = RequestMethod.POST)
    public void update(
            @RequestBody @Validated({ValidationGroups.Req.Update.class, Default.class}) ProductTrademarkEntity entity) {
        productTrademarkService.updateById(entity);
    }

    @Operation(summary = "【品牌管理】删除")
    @RequestMapping(value = "/delete/byId", method = RequestMethod.POST)
    public void deleteById(
            @RequestBody @Validated IdBody idBody) {
        productTrademarkService.deleteById(idBody.getId());
    }
}
