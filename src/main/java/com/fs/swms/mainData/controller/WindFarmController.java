package com.fs.swms.mainData.controller;


import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateWindfarm;
import com.fs.swms.mainData.dto.QueryWindfarm;
import com.fs.swms.mainData.dto.UpdateCustomer;
import com.fs.swms.mainData.service.IWindfarmService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class WindFarmController {

    @Autowired
    private IWindfarmService iWindfarmService;

    @PostMapping("/batch")
    @ApiOperation(value = "批量添加客户风场")
    @AroundLog(name = "批量添加客户风场")
    public Result<?> batchCreate(MyFile file) throws Exception {
        System.out.println(file.getFile().getOriginalFilename());
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
        System.out.println(windfarm.getCustomerName());
        System.out.println(windfarm.getWindFarmList());
        boolean result=iWindfarmService.createWindfarm(windfarm);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }

    }

    @PostMapping("/delete/{customerId}/{windfarmId}")
    @AroundLog(name = "删除客户风场")
//    @ApiImplicitParam(paramType = "path", name = "supplierNo", value = "供应商代码", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("customerId") String customerId,@PathVariable("windfarmId")String windfarmId) {
        if (null == customerId||null==windfarmId) {
            return new Result<>().error("客户ID或风场ID不能为空");
        }
        boolean result = iWindfarmService.deleteWindfarm(customerId,windfarmId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
    @PostMapping("/select/{customerId}")
    @AroundLog(name = "删除客户风场")
    @ApiImplicitParam(paramType = "path", name = "supplierNo", value = "供应商代码", required = true, dataType = "String")
    public QueryWindfarm select(@PathVariable("customerId") String customerId) {

        QueryWindfarm result = iWindfarmService.selectWindfarmByCustomerId(customerId);
        return result;
    }
    @PostMapping("/update")
    @ApiOperation(value = "修改客户风场")
    @AroundLog(name = "修改客户风场")
    public Result<?> update(@RequestBody UpdateCustomer customer)  {
        System.out.println(customer.getCustomerName());
        System.out.println(customer.getTmpWindFarmList());
        boolean result=iWindfarmService.updateWindfarm(customer);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }


    }

}
