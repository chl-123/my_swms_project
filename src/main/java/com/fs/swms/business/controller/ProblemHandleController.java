package com.fs.swms.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.dto.UpdateProblemHandle;
import com.fs.swms.business.entity.ProblemHandle;
import com.fs.swms.business.service.IProblemHandleService;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
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
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/problemHandle")
public class ProblemHandleController {
    @Autowired
    private IProblemHandleService problemHandleService;


    @GetMapping("/all")
    @ApiOperation(value = "查询现场问题处理列表")
    @AroundLog(name = "查询现场问题处理列表")
    public PageResult<ProblemHandle> all(Page<ProblemHandle> page) {
        Page<ProblemHandle> pageProblemHandle = problemHandleService.selectProblemHandleAll(page);
        PageResult<ProblemHandle> pageResult = new PageResult<ProblemHandle>(pageProblemHandle.getTotal(), pageProblemHandle.getRecords());
        return pageResult;
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询全部现场问题处理表")
    @AroundLog(name = "查询全部现场问题处理表")
    public PageResult<ProblemHandle> list(ProblemHandle problemHandle, Page<ProblemHandle> page) {
        Page<ProblemHandle> pageProblemHandle = problemHandleService.selectProblemHandleList(page, problemHandle);
        PageResult<ProblemHandle> pageResult = new PageResult<ProblemHandle>(pageProblemHandle.getTotal(), pageProblemHandle.getRecords());
        return pageResult;
    }

    @GetMapping("/select/{id}")
    @ApiOperation(value = "查询现场问题处理表")
    @AroundLog(name = "查询现场问题处理表")
    public ProblemHandle select(@PathVariable("id")String id) {
        ProblemHandle problemHandle = problemHandleService.selectProblemHandleById(id);
        return problemHandle;
    }
    @PostMapping("/delete/{id}")
    @AroundLog(name = "删除现场问题处理表")
    @ApiImplicitParam(paramType = "path", name = "id", value = "问题ID", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("id") String id) {
        if (null == id) {
            return new Result<>().error("现场问题处理表id不能为空");
        }
        boolean result = problemHandleService.deleteProblemHandle(id);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新现场问题处理表信息")
    @AroundLog(name = "更新现场问题处理表信息")
    public Result<?> update(@RequestBody UpdateProblemHandle problemHandle){
        System.out.println(problemHandle.toString());
        boolean result=problemHandleService.updateProblemHandle(problemHandle);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }

}
