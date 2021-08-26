package com.fs.swms.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.dto.*;
import com.fs.swms.business.entity.ServiceRegister;
import com.fs.swms.business.service.IServiceRegisterService;
import com.fs.swms.common.annotation.auth.CurrentUser;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.security.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping("/serviceRegister")
public class ServiceRegisterController {
    @Autowired
    private IServiceRegisterService serviceRegisterService;

    private String APPENDFILES="appendFiles";
    @PostMapping("/create")
    @ApiOperation(value = "添加服务登记表")
    @AroundLog(name = "添加服务登记表")
    public Result<?> create(CreateServiceRegister serviceRegister, HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(APPENDFILES);
        boolean result=serviceRegisterService.createServiceRegister(serviceRegister,files);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询服务登记表列表")
    @AroundLog(name = "查询服务登记表列表")
    public PageResult<ServiceRegister> all(Page<ServiceRegister> page) {
        Page<ServiceRegister> pageServiceRegister = serviceRegisterService.selectServiceRegisterAll(page);
        PageResult<ServiceRegister> pageResult = new PageResult<ServiceRegister>(pageServiceRegister.getTotal(), pageServiceRegister.getRecords());
        return pageResult;
    }

    @GetMapping("/register/list")
    @ApiOperation(value = "根据条件查询服务登记表")
    @AroundLog(name = "根据条件查询服务登记表")
    public PageResult<ServiceRegisterInfo> list(QueryServiceRegister serviceRegister, Page<ServiceRegisterInfo> page,@CurrentUser User user) {
        Page<ServiceRegisterInfo> pageServiceRegister = serviceRegisterService.selectRegisterList(page, serviceRegister,user);
        PageResult<ServiceRegisterInfo> pageResult = new PageResult<ServiceRegisterInfo>(pageServiceRegister.getTotal(), pageServiceRegister.getRecords());
        return pageResult;
    }

    @GetMapping("/select/{id}")
    @ApiOperation(value = "查询服务登记表")
    @AroundLog(name = "查询服务登记表")
    public Result<ServiceRegisterInfo> select(@PathVariable("id")String id) {
        ServiceRegisterInfo serviceRegister = serviceRegisterService.selectServiceRegisterById(id);
        return new Result<ServiceRegisterInfo>().success().put(serviceRegister);
    }
    @PostMapping("/delete/{approvalSheetId}/{serviceRegisterId}")
    @AroundLog(name = "取消服务登记")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "approvalSheetId", value = "审批表ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "serviceRegisterId", value = "服务登记表ID", required = true, dataType = "String")

    })
    public Result<?> delete(@PathVariable("approvalSheetId") String approvalSheetId,@PathVariable("serviceRegisterId") String serviceRegisterId,@CurrentUser User user) {

        boolean result = serviceRegisterService.deleteServiceRegister(approvalSheetId,serviceRegisterId,user);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新服务登记表信息")
    @AroundLog(name = "更新服务登记表信息")
    public Result<?> update(UpdateServiceRegister serviceRegister, HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(APPENDFILES);
        boolean result=serviceRegisterService.updateServiceRegister(serviceRegister,files);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }
    @PostMapping("/executor")
    @ApiOperation(value = "转办")
    @AroundLog(name = "转办")
    public Result<?> executor(@RequestBody ServiceRegisterExecutor executor,@CurrentUser User user){
        boolean result=serviceRegisterService.executor(executor,user);
        if (result) {
            return new Result<>().success("转办成功");

        }else {
            return new Result<>().error("转办失败，请重试");
        }
    }
    @PostMapping("/approval")
    @ApiOperation(value = "科长审批")
    @AroundLog(name = "科长审批")
    public Result<?> approval(@RequestBody UpdateServiceRegister serviceRegister, @CurrentUser User user){
        boolean result=serviceRegisterService.Approval(serviceRegister,user);
        if (result) {
            return new Result<>().success("审批成功");

        }else {
            return new Result<>().error("审批失败，请重试");
        }
    }
    @PostMapping("/approval/leader")
    @ApiOperation(value = "科长审批")
    @AroundLog(name = "科长审批")
    public Result<?> leaderApproval(@RequestBody UpdateServiceRegister serviceRegister,@CurrentUser User user){
        boolean result=serviceRegisterService.leaderApproval(serviceRegister,user);
        if (result) {
            return new Result<>().success("审批成功");

        }else {
            return new Result<>().error("审批失败，请重试");
        }
    }

    @PostMapping("/distribution")
    @ApiOperation(value = "任务分发")
    @AroundLog(name = "任务分发")
    public Result<?> distribution(@RequestBody List<CreateProblemHandle> organizationIds){

        boolean result=serviceRegisterService.distribution(organizationIds);
        if (result) {
            return new Result<>().success("审批成功");

        }else {
            return new Result<>().error("审批失败，请重试");
        }
    }

}
