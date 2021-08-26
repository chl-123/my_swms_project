package com.fs.swms.mainData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.mainData.dto.CustomerWindFarmInfo;
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

    public boolean createCustomer(Customer customer) {
        boolean result = this.save(customer);
        return result;
    }

    @Override
    public Customer insertCustomer(Customer customer) {
        baseMapper.insert(customer);
        return customer;
    }



    public boolean deleteCustomer(String customerId) {

        boolean result = this.removeById(customerId);
        return result;
    }


    @Override
    public List<Customer> selectCustomersLike(String customerName) {
        QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
        customerQueryWrapper.like("CUSTOMER_NAME",customerName);
        List<Customer> customerList=this.list(customerQueryWrapper);
        return customerList;
    }

    @Override
    public Page<CustomerWindFarmInfo> selectPageList(Page<CustomerWindFarmInfo> page, Customer customer) {
        Page<CustomerWindFarmInfo> customerWindFarmInfoPage = customerMapper.selectCustomerPageByCustomerName(page, customer);
        return customerWindFarmInfoPage;
    }

    public Page<CustomerWindFarmInfo> selectPageAll(Page<CustomerWindFarmInfo> page) {
        Page<CustomerWindFarmInfo> customerWindFarmInfoPage = customerMapper.selectCustomerPageByCustomerName(page, new Customer());
        return customerWindFarmInfoPage;
    }

    @Override
    public List<Customer> selectCustomers(String customerName) {
        QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
        customerQueryWrapper.eq("CUSTOMER_NAME",customerName);
        List<Customer> customerList=this.list(customerQueryWrapper);
        return customerList;
    }

    @Override
    public List<Customer> selectCustomerByCustomerName(String customerName) {
        List<Customer> customerList = customerMapper.selectCustomerListByCustomerName(customerName);
        return customerList;
    }


}
