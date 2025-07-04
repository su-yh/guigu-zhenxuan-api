package com.eb.business.service;

import com.eb.mp.mysql.entity.business.ProductAttrCategoryEntity;
import com.eb.mp.mysql.mapper.business.ProductAttrCategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author suyh
 * @since 2025-04-17
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductAttrCategoryService {
    private final ProductAttrCategoryMapper productAttrCategoryMapper;

    public List<ProductAttrCategoryEntity> listByParentId(@NonNull Long parentId) {
        return productAttrCategoryMapper.listByParentId(parentId);
    }
}
