package com.fs.swms.mainData.service.impl;

import com.fs.swms.mainData.entity.Product;
import com.fs.swms.mainData.mapper.ProductMapper;
import com.fs.swms.mainData.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2021-08-17
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
