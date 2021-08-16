package com.fs.swms.mainData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.mainData.dto.CreateCustomer;
import com.fs.swms.mainData.dto.UpdateCustomer;
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
     * 分页查询角色列表
     * @param page
     * @param Customer
     * @return Page<Customer>
     */
    Page<Customer> selectCustomerList(Page<Customer> page, Customer Customer);

    /**
     * 创建角色
     * @param Customer
     * @return boolean
     */
    boolean createCustomer(CreateCustomer Customer);

    /**
     * 更新角色
     * @param Customer
     * @return boolean
     */
    boolean updateCustomer(UpdateCustomer Customer);

    /**
     * 删除角色
     * @param CustomerId
     * @return boolean
     */
    boolean deleteCustomer(String CustomerId);

    /**
     * 批量删除角色
     * @param CustomerIds
     * @return boolean
     */
    boolean batchDeleteCustomer(List<String> CustomerIds);

    Customer selectCustomers(String CustomerId);
}
