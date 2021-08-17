package com.fs.swms.mainData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.mainData.dto.CustomerWindFarmInfo;
import com.fs.swms.mainData.entity.Customer;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengweihe
 * @since 2021-08-13
 */
public interface CustomerMapper extends BaseMapper<Customer> {
    Page<CustomerWindFarmInfo> selectCustomerByCustomerName(Page<CustomerWindFarmInfo> page, @Param("customerWindFarmInfo") Customer customer);
}
