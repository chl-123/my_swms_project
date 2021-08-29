package com.fs.swms.mainData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateProduct;
import com.fs.swms.mainData.dto.ProductInfo;
import com.fs.swms.mainData.dto.QueryProduct;
import com.fs.swms.mainData.dto.UpdateProduct;
import com.fs.swms.mainData.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

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
     * 创建齿轮箱信息
     * @param product
     * @return boolean
     */
    boolean createProduct(CreateProduct product, List<MultipartFile> files);
    /**
     * 质管科更新齿轮箱信息
     * @param product
     * @param files
     * @return boolean
     */
    boolean updateProductForQM(UpdateProduct product,List<MultipartFile> files);
    /**
     * 营销科更新齿轮箱信息
     * @param product
     * @return boolean
     */
    boolean updateProductForMarketing(UpdateProduct product);
    /**
     * 删除齿轮箱信息
     * @param id
     * @return boolean
     */
    boolean deleteProduct(String id);
    /**
     * 质管科批量添加齿轮箱信息
     * @param file
     * @return boolean
     */
    boolean batchCreateForQM(MyFile file) throws Exception;
    /**
     * 营销科批量添加齿轮箱信息
     * @param file
     * @return boolean
     */
    boolean batchCreateForMarketing(MyFile file) throws Exception;
    /**
     * 通过ID查询齿轮箱信息
     * @param id
     * @return ProductInfo
     */
    ProductInfo selectProductByIdForQuality(String id);
    /**
     * 通过ID查询齿轮箱信息
     * @param id
     * @return boolean
     */
    ProductInfo selectProductByIdForMarketing(String id) throws ParseException;
    /**
     * 根据查询条件查询齿轮箱信息
     * @param product
     * @param page
     * @return Page<ProductInfo>
     */
    Page<ProductInfo> selectList(QueryProduct product, Page<ProductInfo> page);
    /**
     * 查询全部齿轮箱信息
     * @param page
     * @return Page<ProductInfo>
     */
    Page<ProductInfo> selectAll(Page<ProductInfo> page);

    /**
     * 根据齿轮箱编号查询齿轮箱信息
     * @param boxNo
     * @return ProductInfo
     */
    ProductInfo selectProductByBoxNo(String boxNo);

    public List<Product> selectBoxNoList(String boxNo);
}
