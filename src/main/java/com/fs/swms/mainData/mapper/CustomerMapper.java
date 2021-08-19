package com.fs.swms.mainData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.mainData.dto.CustomerWindFarmInfo;
import com.fs.swms.mainData.entity.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-13
 */
public interface CustomerMapper extends BaseMapper<Customer> {
    /**
     * 通过供应商代码删除该供应商
     * @param customer
     * @param page
     * @return Page
     */
    Page<CustomerWindFarmInfo> selectCustomerPageByCustomerName(Page<CustomerWindFarmInfo> page, @Param("customerWindFarmInfo") Customer customer);
    /**
     * 通过供应商代码删除该供应商
     * @param customerName
     * @return List<Customer>
     */
    List<Customer> selectCustomerListByCustomerName(@Param("customerName")String customerName);
}
