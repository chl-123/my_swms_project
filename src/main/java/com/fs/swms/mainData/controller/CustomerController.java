package com.fs.swms.mainData.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.mainData.dto.CustomerWindFarmInfo;
import com.fs.swms.mainData.entity.Customer;
import com.fs.swms.mainData.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/list")
    public PageResult<CustomerWindFarmInfo> list(Customer customer, Page<CustomerWindFarmInfo> page) {
        page.setCurrent(1);
        page.setSize(2);
        Page<CustomerWindFarmInfo> customerWindFarmInfoPage = iCustomerService.selectList(page, customer);
        PageResult<CustomerWindFarmInfo> pageResult = new PageResult<CustomerWindFarmInfo>(customerWindFarmInfoPage.getTotal(), customerWindFarmInfoPage.getRecords());
        return pageResult;
    }

}
