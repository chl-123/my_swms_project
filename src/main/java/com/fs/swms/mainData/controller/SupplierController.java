package com.fs.swms.mainData.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.util.Utils;
import com.fs.swms.mainData.dto.CreateSupplier;
import com.fs.swms.mainData.dto.UpdateSupplier;
import com.fs.swms.mainData.entity.Supplier;
import com.fs.swms.mainData.service.ISupplierService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>A
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @PostMapping("/create")
    @ApiOperation(value = "添加供应商")
    @AroundLog(name = "添加供应商")
    public Result<?> create(@RequestBody CreateSupplier supplier){
        boolean result=supplierService.createSupplier(supplier);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @PostMapping("/batch")
    @ApiOperation(value = "批量添加供应商")
    @AroundLog(name = "批量添加供应商")
    public Result<?> batchCreate(MyFile file) throws Exception {
        boolean result=supplierService.batchCreateSupplier(file);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @GetMapping("/all")
    @ApiOperation(value = "查询全部供应商列表")
    @AroundLog(name = "查询全部供应商列表")
    public PageResult<Supplier> all(@RequestBody Page<Supplier> page) {
        Page<Supplier> pageSupplier = supplierService.selectSupplierAll(page);
        PageResult<Supplier> pageResult = new PageResult<Supplier>(pageSupplier.getTotal(), pageSupplier.getRecords());
        return pageResult;
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询供应商列表")
    @AroundLog(name = "查询供应商列表")
    public PageResult<Supplier> list( Supplier supplier, Page<Supplier> page) {
        Page<Supplier> pageSupplier = supplierService.selectSupplierList(page, supplier);
        PageResult<Supplier> pageResult = new PageResult<Supplier>(pageSupplier.getTotal(), pageSupplier.getRecords());
        return pageResult;
    }
    @PostMapping("/delete/{supplierNo}")
    @AroundLog(name = "删除供应商")
    @ApiImplicitParam(paramType = "path", name = "supplierNo", value = "供应商代码", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("supplierNo") String supplierNo) {
        if (null == supplierNo) {
            return new Result<>().error("供应商代码不能为空");
        }
        boolean result = supplierService.deleteSupplier(supplierNo);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新供应商信息")
    @AroundLog(name = "更新供应商信息")
    public Result<?> update(@RequestBody UpdateSupplier supplier){
        boolean result=supplierService.updateSupplier(supplier);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }
    @PostMapping("/select/{supplierNo}")
    @AroundLog(name = "查询供应商")
    @ApiImplicitParam(paramType = "path", name = "supplierNo", value = "供应商代码", required = true, dataType = "String")
    public Result<Supplier> select(@PathVariable("supplierNo") String supplierNo) {
        Supplier supplier = supplierService.selectBySupplierNo(supplierNo);
        return new Result<Supplier>().success().put(supplier);
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
