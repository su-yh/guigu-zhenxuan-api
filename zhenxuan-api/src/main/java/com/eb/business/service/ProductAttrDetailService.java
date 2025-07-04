package com.eb.business.service;


import com.base.web.exception.ExceptionUtil;
import com.eb.business.dto.product.attr.ProductAttrDetailDto;
import com.eb.constant.enums.ApiErrorCodeEnums;
import com.eb.mp.mysql.entity.business.ProductAttrNameEntity;
import com.eb.mp.mysql.entity.business.ProductAttrValueEntity;
import com.eb.mp.mysql.mapper.business.ProductAttrNameMapper;
import com.eb.mp.mysql.mapper.business.ProductAttrValueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductAttrDetailService {
    private final ProductAttrNameMapper productAttrNameMapper;
    private final ProductAttrValueMapper productAttrValueMapper;

    public List<ProductAttrDetailDto> listByCategory(@NonNull Long categoryId) {
        List<ProductAttrNameEntity> nameEntities = productAttrNameMapper.selectListByCategoryId(categoryId);
        if (nameEntities == null || nameEntities.isEmpty()) {
            return null;
        }

        List<ProductAttrDetailDto> resultList = new ArrayList<>();
        for (ProductAttrNameEntity nameEntity : nameEntities) {
            ProductAttrDetailDto detailDto = new ProductAttrDetailDto();

            List<ProductAttrValueEntity> valueEntities = productAttrValueMapper.selectListByNameId(nameEntity.getId());
            detailDto.setProductAttrNameEntity(nameEntity);
            detailDto.setProductAttrValueEntityList(valueEntities);

            resultList.add(detailDto);
        }

        return resultList;
    }

    @Transient
    public void create(ProductAttrDetailDto dto) {
        ProductAttrNameEntity nameEntity = dto.getProductAttrNameEntity();

        productAttrNameMapper.insert(nameEntity);

        List<ProductAttrValueEntity> valueEntityList = dto.getProductAttrValueEntityList();
        for (ProductAttrValueEntity valueEntity : valueEntityList) {
            valueEntity.setAttrNameId(nameEntity.getId());
        }

        productAttrValueMapper.insertBatch(valueEntityList);
    }

    @Transient
    public void update(ProductAttrDetailDto dto) {
        ProductAttrNameEntity nameEntity = dto.getProductAttrNameEntity();
        ProductAttrNameEntity historyEntity = productAttrNameMapper.selectById(nameEntity.getId());
        if (historyEntity == null) {
            throw ExceptionUtil.business(ApiErrorCodeEnums.RECORD_NOT_EXISTS, nameEntity.getId());
        }

        productAttrNameMapper.updateById(nameEntity);

        productAttrValueMapper.deleteByNameId(nameEntity.getId());

        List<ProductAttrValueEntity> valueEntityList = dto.getProductAttrValueEntityList();
        for (ProductAttrValueEntity valueEntity : valueEntityList) {
            valueEntity.setAttrNameId(nameEntity.getId());
        }
        productAttrValueMapper.insertBatch(valueEntityList);
    }

    @Transient
    public void delete(@NonNull Long nameId) {
        productAttrNameMapper.deleteById(nameId);
        productAttrValueMapper.deleteByNameId(nameId);
    }
}
