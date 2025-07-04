package com.eb.business.dto.product.attr;

import com.eb.mp.mysql.entity.business.ProductAttrNameEntity;
import com.eb.mp.mysql.entity.business.ProductAttrValueEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductAttrDetailDto {
    @NotNull
    private ProductAttrNameEntity productAttrNameEntity;
    @NotEmpty
    private List<ProductAttrValueEntity> productAttrValueEntityList;
}
