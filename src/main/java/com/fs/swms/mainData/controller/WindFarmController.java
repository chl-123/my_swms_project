package com.fs.swms.mainData.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.util.Utils;
import com.fs.swms.mainData.dto.CreateWindfarm;
import com.fs.swms.mainData.dto.CustomerWindFarmInfo;
import com.fs.swms.mainData.dto.QueryWindfarm;
import com.fs.swms.mainData.dto.UpdateCustomer;
import com.fs.swms.mainData.entity.Customer;
import com.fs.swms.mainData.service.ICustomerService;
import com.fs.swms.mainData.service.IWindfarmService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2021-08-16
 */
@RestController
@RequestMapping("/windfarm")
@ApiOperation("客户风场前端控制器")
public class WindFarmController {

    @Autowired
    private IWindfarmService iWindfarmService;
    @Autowired
    private ICustomerService iCustomerService;

    @PostMapping("/batch")
    @ApiOperation(value = "批量添加客户风场")
    @AroundLog(name = "批量添加客户风场")
    public Result<?> batchCreate(MyFile file) throws Exception {
        boolean result=iWindfarmService.batchCreateWindfarm(file);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @PostMapping("/create")
    @ApiOperation(value = "添加客户风场")
    @AroundLog(name = "添加客户风场")
    public Result<?> create(@RequestBody CreateWindfarm windfarm)  {
        boolean result=iWindfarmService.createWindfarm(windfarm);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }

    }
    @PostMapping("/delete/{customerId}")
    @AroundLog(name = "删除客户风场")
    @ApiOperation(value = "根据客户ID和风场ID删除客户风场信息")
    @ApiImplicitParam(paramType = "path", name = "customerId", value = "客户ID", required = true, dataType = "String")

    public Result<?> delete(@PathVariable("customerId") String customerId) {
        if (null == customerId) {
            return new Result<>().error("客户ID不能为空");
        }
        boolean result = iWindfarmService.deleteWindfarm(customerId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
    @GetMapping("/select/{customerId}")
    @AroundLog(name = "根据客户ID查客户风场信息")
    @ApiImplicitParam(paramType = "path", name = "customerId", value = "客户ID", required = true, dataType = "String")
    public Result<QueryWindfarm> select(@PathVariable("customerId") String customerId) {
        QueryWindfarm result = iWindfarmService.selectWindfarmByCustomerId(customerId);

        return new Result<QueryWindfarm>().success().put(result);
    }
    @PostMapping("/update")
    @ApiOperation(value = "修改客户风场")
    @AroundLog(name = "修改客户风场")
    public Result<?> update(@RequestBody UpdateCustomer customer)  {
        boolean result=iWindfarmService.updateWindfarm(customer);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @GetMapping("/list")
    @ApiOperation(value = "分页查询客户风场信息")
    public PageResult<CustomerWindFarmInfo> list(Customer customer, Page<CustomerWindFarmInfo> page) {
        Page<CustomerWindFarmInfo> customerWindFarmInfoPage = iCustomerService.selectPageList(page, customer);
        PageResult<CustomerWindFarmInfo> pageResult = new PageResult<CustomerWindFarmInfo>(customerWindFarmInfoPage.getTotal(), customerWindFarmInfoPage.getRecords());
        return pageResult;
    }
    @GetMapping("/all")
    @ApiOperation(value = "查询全部客户风场信息")
    public PageResult<CustomerWindFarmInfo> all(Page<CustomerWindFarmInfo> page) {
        Page<CustomerWindFarmInfo> customerWindFarmInfoPage = iCustomerService.selectPageAll(page);
        PageResult<CustomerWindFarmInfo> pageResult = new PageResult<CustomerWindFarmInfo>(customerWindFarmInfoPage.getTotal(), customerWindFarmInfoPage.getRecords());
        return pageResult;
    }
    @GetMapping("/download/template")
    @ApiOperation(value = "文件下载")
    @ApiImplicitParam(paramType = "query", name = "fileName", value = "文件名", required = true, dataType = "String")
    public void download(@RequestParam("fileName") String fileName, HttpServletRequest request, HttpServletResponse response)  {
        try{
            Utils.downloadFile(fileName,request,response);
        }catch (Exception e){
            throw new BusinessException("文件下载失败");
        }
    }


}
