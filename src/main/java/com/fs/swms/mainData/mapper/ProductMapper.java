package com.fs.swms.mainData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.mainData.dto.ProductInfo;
import com.fs.swms.mainData.dto.QueryProduct;
import com.fs.swms.mainData.entity.Product;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-17
 */
public interface ProductMapper extends BaseMapper<Product> {
    Page<ProductInfo> selectProductList(Page<ProductInfo> page, @Param("product") QueryProduct product);

}
