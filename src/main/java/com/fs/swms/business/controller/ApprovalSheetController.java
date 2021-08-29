package com.fs.swms.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.dto.ApprovalSheetInfo;
import com.fs.swms.business.dto.CreateApprovalSheet;
import com.fs.swms.business.dto.QueryApprovalSheet;
import com.fs.swms.business.dto.UpdateApprovalSheet;
import com.fs.swms.business.service.IApprovalSheetService;
import com.fs.swms.common.annotation.auth.CurrentUser;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.common.controller.BaseController;
import com.fs.swms.security.entity.User;
import io.swagger.annotations.ApiImplicitParam;
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
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/approvalSheet")
public class ApprovalSheetController extends BaseController {
    @Autowired
    private IApprovalSheetService approvalSheetService;

    private String APPENDFILES="appendFiles";
    @PostMapping("/create")
    @ApiOperation(value = "添加审批表")
    @AroundLog(name = "添加审批表")
    public Result<?> create(CreateApprovalSheet approvalSheet, HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(APPENDFILES);

        boolean result=approvalSheetService.createApprovalSheet(approvalSheet,files);
        if (result) {
            return new Result<>().success("添加成功");

        }else {
            return new Result<>().error("添加失败，请重试");
        }
    }
    @GetMapping("/all")
    @ApiOperation(value = "查询审批单列表")
    public PageResult<ApprovalSheetInfo> all(Page<ApprovalSheetInfo> page) {
        Page<ApprovalSheetInfo> pageApprovalSheet = approvalSheetService.selectApprovalSheetAll(page);
        PageResult<ApprovalSheetInfo> pageResult = new PageResult<ApprovalSheetInfo>(pageApprovalSheet.getTotal(), pageApprovalSheet.getRecords());
        return pageResult;
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询全部审批单")
    public PageResult<ApprovalSheetInfo> list(QueryApprovalSheet approvalSheet, Page<ApprovalSheetInfo> page,@CurrentUser User user) {
        Page<ApprovalSheetInfo> pageApprovalSheet = approvalSheetService.selectApprovalSheetList(page, approvalSheet,user);
        PageResult<ApprovalSheetInfo> pageResult = new PageResult<ApprovalSheetInfo>(pageApprovalSheet.getTotal(), pageApprovalSheet.getRecords());
        return pageResult;
    }

    @PostMapping("/delete/{id}")
    @AroundLog(name = "删除审批单")
    @ApiImplicitParam(paramType = "path", name = "id", value = "问齿轮箱D", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("id") String id) {
        if (null == id) {
            return new Result<>().error("齿轮箱id不能为空");
        }
        boolean result = approvalSheetService.deleteApprovalSheet(id);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
    @PostMapping("/update")
    @ApiOperation(value = "更新审批单信息")
    @AroundLog(name = "更新审批单信息")
    public Result<?> update(UpdateApprovalSheet approvalSheet, HttpServletRequest request){

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(APPENDFILES);
        boolean result=approvalSheetService.updateApprovalSheet(approvalSheet,files);
        if (result) {
            return new Result<>().success("修改成功");

        }else {
            return new Result<>().error("修改失败，请重试");
        }
    }

    @GetMapping("/select/{id}")
    @ApiOperation(value = "查询审批表")
    public Result<ApprovalSheetInfo> select(@PathVariable("id")String id) {
        ApprovalSheetInfo approvalSheetInfo = approvalSheetService.selectApprovalSheetById(id);
        return new Result<ApprovalSheetInfo>().success().put(approvalSheetInfo);
    }

    @PostMapping("/approval")
    @ApiOperation(value = "科长审批")
    @AroundLog(name = "科长审批")
    public Result<?> approval(@RequestBody UpdateApprovalSheet approvalSheet,@CurrentUser User user){
        boolean result=approvalSheetService.Approval(approvalSheet,user);
        if (result) {
            return new Result<>().success("审批成功");

        }else {
            return new Result<>().error("审批失败，请重试");
        }
    }

    @PostMapping("/cancel")
    @ApiOperation(value = "科长取消审批")
    @AroundLog(name = "科长取消审批")
    public Result<?> cancel(@RequestParam (value = "id")String id,@CurrentUser User user){
        boolean result=approvalSheetService.cancelApproval(id,user);
        if (result) {
            return new Result<>().success("取消审批成功");

        }else {
            return new Result<>().error("取消审批失败，请重试");
        }
    }




}
