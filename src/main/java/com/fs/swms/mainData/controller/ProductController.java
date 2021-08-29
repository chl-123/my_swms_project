package com.fs.swms.mainData.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.controller.BaseController;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateProduct;
import com.fs.swms.mainData.dto.ProductInfo;
import com.fs.swms.mainData.dto.QueryProduct;
import com.fs.swms.mainData.dto.UpdateProduct;
import com.fs.swms.mainData.entity.Product;
import com.fs.swms.mainData.service.IProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

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
public class ProductController extends BaseController {
    @Autowired
    private IProductService iProductService;

    @PostMapping("/batch/quality")
    @ApiOperation(value = "质管科批量添加齿轮箱")
    @AroundLog(name = "质管科批量添加齿轮箱")
    public Result<?> batchCreateForQM(MyFile file) throws Exception {
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
    public Result<?> create(CreateProduct product, HttpServletRequest request)  {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("appendFiles");

        boolean result=iProductService.createProduct(product,files);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @PostMapping("/update/quality")
    @ApiOperation(value = "质管更新齿轮箱")
    @AroundLog(name = "质管更新齿轮箱")
    public Result<?> updateForQM(UpdateProduct product, HttpServletRequest request)  {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("appendFiles");
        boolean result=iProductService.updateProductForQM(product,files);
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
    @ApiOperation(value = "删除齿轮箱信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "齿轮箱ID", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("id") String id) {

        boolean result=iProductService.deleteProduct(id);
        if (result) {
            return new Result<>().success("删除成功");

        }else {
            return new Result<>().error("删除失败，请重试");
        }
    }
    @GetMapping("/select/quality/{id}")
    @AroundLog(name = "根据齿轮箱ID，查询齿轮箱信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "齿轮箱ID", required = true, dataType = "String")
    public Result<ProductInfo> selectForQuality(@PathVariable("id") String id) {

        ProductInfo result=iProductService.selectProductByIdForQuality(id);
        return new Result<ProductInfo>().success().put(result);
    }
    @GetMapping("/select/marketing/{id}")
    @AroundLog(name = "营销科根据齿轮箱ID，查询齿轮箱信息")
    @ApiOperation(value = "营销科根据齿轮箱ID，查询齿轮箱信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "齿轮箱ID", required = true, dataType = "String")
    public Result<ProductInfo> selectForMarketing(@PathVariable("id") String id) throws ParseException {

        ProductInfo result=iProductService.selectProductByIdForMarketing(id);
        return new Result<ProductInfo>().success().put(result);
    }
    @GetMapping("/list")
    @ApiOperation(value = "根据条件查询齿轮箱信息")
    public PageResult<ProductInfo> list(QueryProduct product, Page<ProductInfo> page) throws ParseException {
        Page<ProductInfo> productInfoPage = iProductService.selectList(product,page);
        PageResult<ProductInfo> pageResult = new PageResult<ProductInfo>(productInfoPage.getTotal(), productInfoPage.getRecords());
        return pageResult;
    }


    @GetMapping("/all")
    @ApiOperation(value = "查询全部齿轮箱信息")
    public PageResult<ProductInfo> all(Page<ProductInfo> page) throws ParseException {
        Page<ProductInfo> productInfoPage = iProductService.selectAll(page);
        PageResult<ProductInfo> pageResult = new PageResult<ProductInfo>(productInfoPage.getTotal(), productInfoPage.getRecords());
        return pageResult;
    }
    @GetMapping("/select/boxNo")
    @ApiImplicitParam(paramType = "query", name = "boxNo", value = "齿轮箱编号", required = true, dataType = "String")
    public Result<ProductInfo> selectByBoxNo(@RequestParam("boxNo") String boxNo) {

        ProductInfo result=iProductService.selectProductByBoxNo(boxNo);
        return new Result<ProductInfo>().success().put(result);
    }
    @GetMapping("/select/boxNoList")
    @ApiOperation(value = "根据齿轮箱编号模糊查询获取齿轮箱编号列表")
    @ApiImplicitParam(paramType = "query", name = "boxNo", value = "齿轮箱编号", required = true, dataType = "String")
    public Result<List<Product>> selectBoxNoList(@RequestParam("boxNo") String boxNo) {
        List<Product> result=iProductService.selectBoxNoList(boxNo);
        return new Result<List<Product>>().success().put(result);
    }
}
