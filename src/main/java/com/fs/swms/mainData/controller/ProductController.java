package com.fs.swms.mainData.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateProduct;
import com.fs.swms.mainData.dto.ProductInfo;
import com.fs.swms.mainData.dto.QueryProduct;
import com.fs.swms.mainData.dto.UpdateProduct;
import com.fs.swms.mainData.service.IProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @PostMapping("/batch/quality")
    @ApiOperation(value = "质管科批量添加齿轮箱")
    @AroundLog(name = "质管科批量添加齿轮箱")
    public Result<?> batchCreateForQM(MyFile file) throws Exception {
        System.out.println(file.getFile().getOriginalFilename());
        boolean result=iProductService.batchCreateForQM(file);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @PostMapping("/batch/marketing")
    @ApiOperation(value = "营销科批量添加齿轮箱")
    @AroundLog(name = "营销科批量添加齿轮箱")
    public Result<?> batchCreateForMarketing(MyFile file) throws Exception {
        System.out.println(file.getFile().getOriginalFilename());
        boolean result=iProductService.batchCreateForMarketing(file);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @PostMapping("/create")
    @ApiOperation(value = "质管添加齿轮箱")
    @AroundLog(name = "质管添加齿轮箱")
    public Result<?> create(@RequestParam("product") String product,  MyFile file)  {
        CreateProduct createProduct = JSON.parseObject(product, CreateProduct.class);
        boolean result=iProductService.createProduct(createProduct,file);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @PostMapping("/update/quality")
    @ApiOperation(value = "质管更新齿轮箱")
    @AroundLog(name = "质管更新齿轮箱")
    public Result<?> updateForQM(@RequestParam("product") String product,  MyFile file)  {
        UpdateProduct updateProduct = JSON.parseObject(product, UpdateProduct.class);
        boolean result=iProductService.updateProductForQM(updateProduct,file);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }
    @PostMapping("/update/marketing")
    @ApiOperation(value = "营销科更新齿轮箱")
    @AroundLog(name = "营销科更新齿轮箱")
    public Result<?> updateForMarketing(@RequestBody UpdateProduct product)  {
        boolean result=iProductService.updateProductForMarketing(product);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }
    @PostMapping("/delete/{id}")
    @AroundLog(name = "删除齿轮箱信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "齿轮箱ID", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("id") String id) {

        boolean result=iProductService.deleteProduct(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @PostMapping("/select/quality/{id}")
    @AroundLog(name = "根据齿轮箱ID，查询齿轮箱信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "齿轮箱ID", required = true, dataType = "String")
    public Result<ProductInfo> selectForQuality(@PathVariable("id") String id) {

        ProductInfo result=iProductService.selectProductByIdForQuality(id);
        return new Result<ProductInfo>().success().put(result);
    }
    @PostMapping("/select/marketing/{id}")
    @AroundLog(name = "营销科根据齿轮箱ID，查询齿轮箱信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "齿轮箱ID", required = true, dataType = "String")
    public Result<ProductInfo> selectForMarketing(@PathVariable("id") String id) {

        ProductInfo result=iProductService.selectProductByIdForMarketing(id);
        return new Result<ProductInfo>().success().put(result);
    }
    @GetMapping("/list")
    public PageResult<ProductInfo> list(QueryProduct product, Page<ProductInfo> page) throws ParseException {
        page.setCurrent(1);
        page.setSize(3);
        Page<ProductInfo> productInfoPage = iProductService.list(product,page);
        PageResult<ProductInfo> pageResult = new PageResult<ProductInfo>(productInfoPage.getTotal(), productInfoPage.getRecords());
        return pageResult;
    }
}
