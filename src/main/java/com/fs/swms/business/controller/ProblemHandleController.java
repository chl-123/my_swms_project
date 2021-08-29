package com.fs.swms.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.dto.*;
import com.fs.swms.business.service.IProblemHandleService;
import com.fs.swms.common.annotation.auth.CurrentUser;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.controller.BaseController;
import com.fs.swms.security.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/problemHandle")
public class ProblemHandleController extends BaseController {
    @Autowired
    private IProblemHandleService problemHandleService;

    private String APPENDFILES="appendFiles";
    @PostMapping("/handle")
    @ApiOperation(value = "问题处理")
    @AroundLog(name = "问题处理")
    public Result<?> handle(CreateProblemHandle problemHandle, HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(APPENDFILES);
        boolean result=problemHandleService.handle(problemHandle,files);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }


    @PostMapping("/transfer")
    @ApiOperation(value = "转办")
    @AroundLog(name = "转办")
    public Result<?> transfer(@RequestBody CreateProblemHandle problemHandle, @CurrentUser User user){
        boolean result=problemHandleService.transfer(problemHandle,user);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }
    @PostMapping("/cancel")
    @ApiOperation(value = "取消")
    @AroundLog(name = "取消")
    public Result<?> cancel(@RequestParam("problemHandleId")String problemHandleId,@RequestParam("approvalSheetId")String approvalSheetId,@CurrentUser User user){
        boolean result=problemHandleService.cancel(problemHandleId,approvalSheetId,user);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }


    @GetMapping("/list")
    @ApiOperation(value = "查询全部现场问题处理表")
    public PageResult<ProblemHandleInfo> list(QueryProblemHandle problemHandle, Page<ProblemHandleInfo> page,@CurrentUser User user) {
        Page<ProblemHandleInfo> pageProblemHandle = problemHandleService.selectProblemHandleList(page, problemHandle,user);
        PageResult<ProblemHandleInfo> pageResult = new PageResult<ProblemHandleInfo>(pageProblemHandle.getTotal(), pageProblemHandle.getRecords());
        return pageResult;
    }

    @GetMapping("/management/list")
    @ApiOperation(value = "查询全部现场问题处理表")
    public PageResult<ProblemHandleInfo> selectPHForManagement(QueryProblemHandle problemHandle, Page<ProblemHandleInfo> page,@CurrentUser  User user) {
        Page<ProblemHandleInfo> pageProblemHandle = problemHandleService.selectProblemHandleListForManagement(page, problemHandle,user);
        PageResult<ProblemHandleInfo> pageResult = new PageResult<ProblemHandleInfo>(pageProblemHandle.getTotal(), pageProblemHandle.getRecords());
        return pageResult;
    }

    @GetMapping("/select/{id}")
    @ApiOperation(value = "查询现场问题处理表")
    public Result<ProblemHandleInfoEntity> select(@PathVariable("id")String id) {
        ProblemHandleInfoEntity problemHandle = problemHandleService.selectProblemHandleById(id);
        return new Result<ProblemHandleInfoEntity>().success().put(problemHandle);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新现场问题处理表信息")
    @AroundLog(name = "更新现场问题处理表信息")
    public Result<?> update(UpdateProblemHandle problemHandle, HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(APPENDFILES);
        boolean result=problemHandleService.updateProblemHandle(problemHandle,files);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }


}
