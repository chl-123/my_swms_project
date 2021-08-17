package com.fs.swms.mainData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.mainData.dto.CustomerWindFarmInfo;
import com.fs.swms.mainData.entity.Customer;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2021-08-13
 */
public interface ICustomerService extends IService<Customer> {


    /**
     * 创建角色
     * @param customer
     * @return boolean
     */
    boolean createCustomer(Customer customer);

    Customer insertCustomer(Customer customer);

    /**
     * 删除角色
     * @param CustomerId
     * @return boolean
     */
    boolean deleteCustomer(String CustomerId);


    List<Customer> selectCustomers(String customerName);

    Page<CustomerWindFarmInfo> selectList(Page<CustomerWindFarmInfo> page, Customer customer);
}
