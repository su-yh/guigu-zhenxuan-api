package com.eb.business.service;

import com.base.mp.mybatis.PageParam;
import com.base.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.ProductTrademarkEntity;
import com.eb.mp.mysql.mapper.business.ProductTrademarkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductTrademarkService {
    private final ProductTrademarkMapper productTrademarkMapper;

    public PageResult<ProductTrademarkEntity> listPage(
            PageParam pageParam, ProductTrademarkEntity queryEntity) {
        return productTrademarkMapper.listPage(pageParam, queryEntity);
    }

    public ProductTrademarkEntity queryById(Long id) {
        return productTrademarkMapper.selectById(id);
    }

    public void create(ProductTrademarkEntity entity) {
        productTrademarkMapper.insert(entity);
    }

    public void updateById(ProductTrademarkEntity entity) {
        productTrademarkMapper.updateById(entity);
    }

    public void deleteById(Long id) {
        productTrademarkMapper.deleteById(id);
    }
}
