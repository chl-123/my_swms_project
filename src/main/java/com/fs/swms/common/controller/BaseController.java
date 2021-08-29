package com.fs.swms.common.controller;

import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.util.Utils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
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
