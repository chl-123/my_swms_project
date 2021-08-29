package com.fs.swms.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.business.dto.ApprovalSheetInfo;
import com.fs.swms.business.dto.CreateApprovalSheet;
import com.fs.swms.business.dto.QueryApprovalSheet;
import com.fs.swms.business.dto.UpdateApprovalSheet;
import com.fs.swms.business.entity.ApprovalSheet;
import com.fs.swms.security.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chl
 * @since 2021-08-18
 */
public interface IApprovalSheetService extends IService<ApprovalSheet> {

    /**
     * 创建审批单
     *
     * @param approvalSheet
     * @return boolean
     */
    boolean createApprovalSheet(CreateApprovalSheet approvalSheet, List<MultipartFile> files);

    /**
     * 分页查询审批单列表（带条件）
     *
     * @param page
     * @param approvalSheet
     * @return Page<Role>
     */
    Page<ApprovalSheetInfo> selectApprovalSheetList(Page<ApprovalSheetInfo> page, QueryApprovalSheet approvalSheet,User user);

    /**
     * 分页查询审批单列表（全部）
     *
     * @param page
     * @return Page<Role>
     */
    Page<ApprovalSheetInfo> selectApprovalSheetAll(Page<ApprovalSheetInfo> page);

    /**
     * 根据id删除审批单
     *
     * @param id
     * @return boolean
     */
    boolean deleteApprovalSheet(String id);

    /**
     * 更新审批单
     *
     * @param approvalSheet
     * @return boolean
     */
    boolean updateApprovalSheet(UpdateApprovalSheet approvalSheet, List<MultipartFile> files);

    /**
     * 根据id查询审批单
     *
     * @param approvalSheetId
     * @return ApprovalSheet
     */
    ApprovalSheetInfo selectApprovalSheetById(String approvalSheetId);
    /**
     * 领导审批
     *
     * @param updateApprovalSheet
     * @return boolean
     */
    boolean Approval(UpdateApprovalSheet updateApprovalSheet, User user);
    /**
     * 领导取消审批
     *
     * @param approvalSheetId
     * @return boolean
     */
    boolean cancelApproval(String approvalSheetId,User user);

}
