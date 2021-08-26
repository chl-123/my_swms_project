package com.fs.swms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.dto.ApprovalSheetInfo;
import com.fs.swms.business.dto.QueryApprovalSheet;
import com.fs.swms.business.entity.ApprovalSheet;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-18
 */
public interface ApprovalSheetMapper extends BaseMapper<ApprovalSheet> {


    /**
     * 查询审批单列表（带条件）
     * @param page
     * @param approvalSheet
     * @return Page<ApprovalSheet>
     */
    Page<ApprovalSheetInfo> selectApprovalSheetList(Page<ApprovalSheetInfo> page, QueryApprovalSheet approvalSheet);






}
