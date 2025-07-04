package com.eb.mp.mysql.mapper.business;

import com.base.mp.mybatis.BaseMapperX;
import com.base.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mysql.entity.business.ProductAttrValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper
public interface ProductAttrValueMapper extends BaseMapperX<ProductAttrValueEntity> {
    default List<ProductAttrValueEntity> selectListByNameId(@Nullable Long nameId) {
        if (nameId == null) {
            return null;
        }

        LambdaQueryWrapperX<ProductAttrValueEntity> queryWrapperX = build();
        queryWrapperX.eq(ProductAttrValueEntity::getAttrNameId, nameId);

        return selectList(queryWrapperX);
    }

    default void deleteByNameId(@Nullable Long nameId) {
        if (nameId == null) {
            return;
        }

        LambdaQueryWrapperX<ProductAttrValueEntity> queryWrapperX = build();
        queryWrapperX.eq(ProductAttrValueEntity::getAttrNameId, nameId);
        delete(queryWrapperX);
    }
}
