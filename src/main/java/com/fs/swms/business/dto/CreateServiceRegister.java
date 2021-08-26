package com.fs.swms.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bai
 * @creat 2021-08-23-14:17
 */
@Data
public class CreateServiceRegister {
    private String id;
    private String approvalSheetId;//审批单表主键
    private String executor;//转办人
    private String serviceNo;//服务编号
    private String serviceType;//服务分类
    private String serviceLevel;//信息等级
    private String problemId;//问题分类表主键
    private String typeName;
    private String isShangxiu;//是否商修
    private String repairman;//现场服务人员
    private String disposalMeasures;//处置措施
    private String filenames;//附件地址
    private String approvalStatus;//科长审批状态
    private String approvalComments;//科长审批意见
    private String approvaer;//科长审批人
    private String approvalDate;//科长审批时间
    private String leaderApprovalStatus;//主管领导审批状态
    private String leaderApprovalComments;//主管领导审批意见
    private String leaderApprovaer;//主管领导审批人
    private String leaderApprovalDate;//主管领导审批时间
    private String childSetp;//当前步骤
    private Date createTime;
    private String creator;
    private Date updateTime;
    private String operator;
    private String delFlag;
}
