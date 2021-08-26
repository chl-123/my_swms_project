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
     * 创建客户信息
     * @param customer
     * @return boolean
     */
    boolean createCustomer(Customer customer);
    /**
     * 创建客户信息并且返回该客户
     * @param customer
     * @return Customer
     */
    Customer insertCustomer(Customer customer);

    /**
     * 通过客户ID删除客户
     * @param CustomerId
     * @return boolean
     */
    boolean deleteCustomer(String CustomerId);

    /**
     * 根据客户名称模糊查询
     * @param customerName
     * @return List<Customer>
     */
    List<Customer> selectCustomersLike(String customerName);
    /**
     * 根据条件模糊查询客户风场信息
     * @param customer
     * @param page
     * @return Page<CustomerWindFarmInfo>
     */
    Page<CustomerWindFarmInfo> selectPageList(Page<CustomerWindFarmInfo> page, Customer customer);
    /**
     * 根据客户名称查客户信息
     * @param customerName
     * @return List<Customer>
     */
    List<Customer> selectCustomers(String customerName);
    /**
     * 创建角色
     * @param customerName
     * @return List<Customer>
     */
    List<Customer> selectCustomerByCustomerName(String customerName);
    /**
     * 查询全部客户风场信息
     * @param page
     * @return Page<CustomerWindFarmInfo>
     */
    Page<CustomerWindFarmInfo> selectPageAll(Page<CustomerWindFarmInfo> page);
}