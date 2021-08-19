package com.fs.swms.mainData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateProduct;
import com.fs.swms.mainData.dto.ProductInfo;
import com.fs.swms.mainData.dto.QueryProduct;
import com.fs.swms.mainData.dto.UpdateProduct;
import com.fs.swms.mainData.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2021-08-17
 */
public interface IProductService extends IService<Product> {
    /**
     * 创建客户风场
     * @param product
     * @return boolean
     */
    boolean createProduct(CreateProduct product, MyFile file);

    boolean updateProductForQM(UpdateProduct product,MyFile file);

    boolean updateProductForMarketing(UpdateProduct product);

    boolean deleteProduct(String id);

    boolean batchCreateForQM(MyFile file) throws Exception;

    boolean batchCreateForMarketing(MyFile file) throws Exception;

    ProductInfo selectProductByIdForQuality(String id);

    ProductInfo selectProductByIdForMarketing(String id);

    Page<ProductInfo> list(QueryProduct product, Page<ProductInfo> page);
}
