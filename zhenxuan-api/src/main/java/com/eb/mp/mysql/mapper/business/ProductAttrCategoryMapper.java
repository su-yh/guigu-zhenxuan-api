package com.eb.mp.mysql.mapper.business;

import com.eb.mp.mybatis.BaseMapperX;
import com.eb.mp.mybatis.LambdaQueryWrapperX;
import com.eb.mp.mysql.entity.business.ProductAttrCategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author suyh
 * @since 2025-04-17
 */
@Mapper
public interface ProductAttrCategoryMapper extends BaseMapperX<ProductAttrCategoryEntity> {
    default List<ProductAttrCategoryEntity> listByParentId(@Nullable Long parentId) {
        if (parentId == null) {
            return null;
        }
        LambdaQueryWrapperX<ProductAttrCategoryEntity> queryWrapperX = build();

        queryWrapperX.eq(ProductAttrCategoryEntity::getParentId, parentId);

        return selectList(queryWrapperX);
    }
}
