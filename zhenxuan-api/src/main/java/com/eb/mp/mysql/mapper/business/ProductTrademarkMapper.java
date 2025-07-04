package com.eb.mp.mysql.mapper.business;

import com.base.mp.mybatis.BaseMapperX;
import com.base.mp.mybatis.LambdaQueryWrapperX;
import com.base.mp.mybatis.PageParam;
import com.base.mp.mybatis.PageResult;
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
