package com.fs.swms.mainData.controller;


import com.fs.swms.common.base.Result;
import com.fs.swms.mainData.entity.Customer;
import com.fs.swms.mainData.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengweihe
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;
    @GetMapping("/select/{customerId}")
    public Result<Customer> select(@PathVariable("customerId") String customerId){
        Customer customer = iCustomerService.selectCustomers(customerId);
        return new Result<Customer>().success().put(customer);
    }
}
