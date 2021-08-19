package com.fs.swms.mainData.controller;


import com.fs.swms.common.base.Result;
import com.fs.swms.mainData.entity.Customer;
import com.fs.swms.mainData.service.ICustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


    @PostMapping("/select")
    @ApiOperation("根据客户名称查询客户信息")
    public Result<List<Customer>> selectByCustomerName(@RequestParam(value = "customerName",required = false) String customerName){
        List<Customer> customerList = iCustomerService.selectCustomerByCustomerName(customerName);
        return new Result<List<Customer>>().success().put(customerList);
    }

}
