package com.fs.swms.mainData.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.mainData.dto.CreateCustomer;
import com.fs.swms.mainData.dto.UpdateCustomer;
import com.fs.swms.mainData.entity.Customer;
import com.fs.swms.mainData.mapper.CustomerMapper;
import com.fs.swms.mainData.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengweihe
 * @since 2021-08-13
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
    @Autowired
    CustomerMapper customerMapper;


    public Page<Customer> selectCustomerList(Page<Customer> page, Customer Customer) {
        return null;
    }


    public boolean createCustomer(CreateCustomer Customer) {
        return false;
    }


    public boolean updateCustomer(UpdateCustomer Customer) {
        return false;
    }


    public boolean deleteCustomer(String CustomerId) {
        return false;
    }


    public boolean batchDeleteCustomer(List<String> CustomerIds) {
        return false;
    }


    public Customer selectCustomers(String CustomerId) {
        Customer customer = customerMapper.selectById(CustomerId);
        return customer;
    }
}
