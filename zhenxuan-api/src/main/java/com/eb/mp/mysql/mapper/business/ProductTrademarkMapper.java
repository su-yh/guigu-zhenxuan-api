package com.eb.mp.mysql.mapper.business;

import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.ProductTrademarkEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductTrademarkMapper extends BaseMapperX<ProductTrademarkEntity> {
    default PageResult<ProductTrademarkEntity> listPage(
            PageParam pageParam, ProductTrademarkEntity queryEntity) {
        LambdaQueryWrapperX<ProductTrademarkEntity> queryWrapperX = build();
        queryWrapperX.eqIfPresent(ProductTrademarkEntity::getId, queryEntity.getId());
        queryWrapperX.eqIfPresent(ProductTrademarkEntity::getName, queryEntity.getName());
        return selectPage(pageParam, queryWrapperX);
    }
}
