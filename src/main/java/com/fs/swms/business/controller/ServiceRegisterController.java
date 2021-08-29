package com.fs.swms.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.dto.*;
import com.fs.swms.business.service.IServiceRegisterService;
import com.fs.swms.common.annotation.auth.CurrentUser;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.controller.BaseController;
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
public class ServiceRegisterController extends BaseController {
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
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }


    @GetMapping("/register/list")
    @ApiOperation(value = "根据条件查询服务登记表")
    public PageResult<ServiceRegisterInfo> list(QueryServiceRegister serviceRegister, Page<ServiceRegisterInfo> page,@CurrentUser User user) {
        Page<ServiceRegisterInfo> pageServiceRegister = serviceRegisterService.selectRegisterList(page, serviceRegister,user);
        PageResult<ServiceRegisterInfo> pageResult = new PageResult<ServiceRegisterInfo>(pageServiceRegister.getTotal(), pageServiceRegister.getRecords());
        return pageResult;
    }
    @GetMapping("/management/list")
    @ApiOperation(value = "根据条件查询服务登记表")
    public PageResult<ServiceRegisterInfo> selectRegManagementList(QueryServiceRegisterManagement serviceRegister, Page<ServiceRegisterInfo> page,@CurrentUser User user) {
        Page<ServiceRegisterInfo> pageServiceRegister = serviceRegisterService.selectRegListForManagement(page, serviceRegister,user);
        PageResult<ServiceRegisterInfo> pageResult = new PageResult<ServiceRegisterInfo>(pageServiceRegister.getTotal(), pageServiceRegister.getRecords());
        return pageResult;
    }
    @GetMapping("/service/approval/list")
    @ApiOperation(value = "根据条件查询服务登记表")
    public PageResult<ServiceRegisterInfo> selectRegApprovalList(QueryServiceRegisterManagement serviceRegister, Page<ServiceRegisterInfo> page) {
        Page<ServiceRegisterInfo> pageServiceRegister = serviceRegisterService.selectRegListForApproval(page, serviceRegister);
        PageResult<ServiceRegisterInfo> pageResult = new PageResult<ServiceRegisterInfo>(pageServiceRegister.getTotal(), pageServiceRegister.getRecords());
        return pageResult;
    }
    @GetMapping("/distribution/list")
    @ApiOperation(value = "根据条件查询服务登记表")
    public PageResult<ServiceRegisterInfo> selectRegDistributionList(QueryServiceRegisterManagement serviceRegister, Page<ServiceRegisterInfo> page) {
        Page<ServiceRegisterInfo> pageServiceRegister = serviceRegisterService.selectRegListForDistribution(page, serviceRegister);
        PageResult<ServiceRegisterInfo> pageResult = new PageResult<ServiceRegisterInfo>(pageServiceRegister.getTotal(), pageServiceRegister.getRecords());
        return pageResult;
    }

    @GetMapping("/distribution/management/list")
    @ApiOperation(value = "根据条件查询服务登记表")
    public PageResult<ProblemHandleInfo> selectRegDistributionManagementList(QueryProblemHandle problemHandle, Page<ProblemHandleInfo> page) {
        Page<ProblemHandleInfo> pageServiceRegister = serviceRegisterService.selectRegListForDistributionManagement(page, problemHandle);
        PageResult<ProblemHandleInfo> pageResult = new PageResult<ProblemHandleInfo>(pageServiceRegister.getTotal(), pageServiceRegister.getRecords());
        return pageResult;
    }
    @GetMapping("/select/{id}")
    @ApiOperation(value = "查询服务登记表")
    public Result<ServiceRegisterInfo> select(@PathVariable("id")String id) {
        ServiceRegisterInfo serviceRegister = serviceRegisterService.selectServiceRegisterById(id);
        return new Result<ServiceRegisterInfo>().success().put(serviceRegister);
    }
    @PostMapping("/delete/register/{approvalSheetId}/{serviceRegisterId}")
    @AroundLog(name = "取消服务登记")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "approvalSheetId", value = "审批表ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "serviceRegisterId", value = "服务登记表ID", required = true, dataType = "String")

    })
    public Result<?> delete(@PathVariable("approvalSheetId") String approvalSheetId,@PathVariable("serviceRegisterId") String serviceRegisterId,@CurrentUser User user) {

        boolean result = serviceRegisterService.deleteServiceRegister(approvalSheetId,serviceRegisterId,user);
        if (result) {
            return new Result<>().success("操作成功");
        } else {
            return new Result<>().error("操作失败");
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新服务登记表信息")
    @AroundLog(name = "更新服务登记表信息")
    public Result<?> update(UpdateServiceRegister serviceRegister, HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(APPENDFILES);
        boolean result=serviceRegisterService.updateServiceRegister(serviceRegister,files);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }
    @PostMapping("/transfer")
    @ApiOperation(value = "转办")
    @AroundLog(name = "转办")
    public Result<?> transfer(@RequestBody ServiceRegisterTransfer executor, @CurrentUser User user){
        boolean result=serviceRegisterService.transfer(executor,user);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }
    @PostMapping("/approval")
    @ApiOperation(value = "审批")
    @AroundLog(name = "审批")
    public Result<?> approval(@RequestBody UpdateServiceRegister serviceRegister, @CurrentUser User user){
        boolean result=serviceRegisterService.Approval(serviceRegister,user);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }

    @PostMapping("/approval/cancel")
    @ApiOperation(value = "取消审批")
    @AroundLog(name = "取消审批")
    public Result<?> cancel(@RequestParam (value = "id")String id,@CurrentUser User user){
        boolean result=serviceRegisterService.cancelApproval(id,user);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }


    @PostMapping("/distribution")
    @ApiOperation(value = "任务分发")
    @AroundLog(name = "任务分发")
    public Result<?> distribution(@RequestBody Distribution problemHandles, @CurrentUser User user){

        boolean result=serviceRegisterService.distribution(problemHandles,user);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }
    @PostMapping("/distribution/cancel")
    @ApiOperation(value = "取消任务分发")
    @AroundLog(name = "取消任务分发")
    public Result<?> cancelDistribution(@RequestParam (value = "approvalSheetId")String approvalSheetId,@CurrentUser User user){

        boolean result=serviceRegisterService.cancelDistribution(approvalSheetId,user);
        if (result) {
            return new Result<>().success("操作成功");

        }else {
            return new Result<>().error("操作失败");
        }
    }
    @PostMapping("/delete/distribution/{approvalSheetId}/{problemHandleId}")
    @AroundLog(name = "删除任务分发")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "approvalSheetId", value = "审批表ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "problemHandleId", value = "问题处理表ID", required = true, dataType = "String")

    })
    public Result<?> deleteDistribution(@PathVariable("approvalSheetId") String approvalSheetId,@PathVariable("problemHandleId") String problemHandleId,@CurrentUser User user) {

        boolean result = serviceRegisterService.deleteProblemHandle(approvalSheetId,problemHandleId,user);
        if (result) {
            return new Result<>().success("操作成功");
        } else {
            return new Result<>().error("操作失败");
        }
    }


}
