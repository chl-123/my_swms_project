package com.fs.swms.mainData.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateProblemType;
import com.fs.swms.mainData.dto.UpdateProblemType;
import com.fs.swms.mainData.entity.ProblemType;
import com.fs.swms.mainData.service.IProblemTypeService;
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
@RequestMapping("/problemType")
@ApiOperation("问题类型前端控制器")
public class ProblemTypeController {
    @Autowired
    private IProblemTypeService problemTypeService;

    @PostMapping("/create")
    @ApiOperation(value = "添加问题")
    @AroundLog(name = "添加问题")
    public Result<?> create(@RequestBody CreateProblemType problemType){
        System.out.println(problemType.toString());
        boolean result=problemTypeService.createProblemType(problemType);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @PostMapping("/batch")
    @ApiOperation(value = "批量添加问题")
    @AroundLog(name = "批量添加问题")
    public Result<?> batchCreate( MyFile file) throws Exception {
        boolean result=problemTypeService.batchCreatProblemType(file);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @GetMapping("/all")
    @ApiOperation(value = "查询问题列表")
    public PageResult<ProblemType> all(Page<ProblemType> page) {
        Page<ProblemType> pageProblemType = problemTypeService.selectProblemTypeAll(page);
        PageResult<ProblemType> pageResult = new PageResult<ProblemType>(pageProblemType.getTotal(), pageProblemType.getRecords());
        return pageResult;
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询全部问题")
    public PageResult<ProblemType> list(ProblemType problemType, Page<ProblemType> page) {
        Page<ProblemType> pageProblemType = problemTypeService.selectProblemTypeList(page, problemType);
        PageResult<ProblemType> pageResult = new PageResult<ProblemType>(pageProblemType.getTotal(), pageProblemType.getRecords());
        return pageResult;
    }

    @GetMapping("/select/{id}")
    @ApiOperation(value = "查询问题名称")
    @ApiImplicitParam(paramType = "path", name = "id", value = "问题ID", required = true, dataType = "String")
    public ProblemType select(@PathVariable("id")String id) {
        ProblemType problemType = problemTypeService.selectProblemTypeById(id);
        return problemType;
    }
    @PostMapping("/delete/{id}")
    @AroundLog(name = "删除问题")
    @ApiImplicitParam(paramType = "path", name = "id", value = "问题ID", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("id") String id) {
        if (null == id) {
            return new Result<>().error("问题id不能为空");
        }
        boolean result = problemTypeService.deleteProblemType(id);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新问题信息")
    @AroundLog(name = "更新问题信息")
    public Result<?> update(@RequestBody UpdateProblemType problemType){
        System.out.println(problemType.toString());
        boolean result=problemTypeService.updateProblemType(problemType);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }


}