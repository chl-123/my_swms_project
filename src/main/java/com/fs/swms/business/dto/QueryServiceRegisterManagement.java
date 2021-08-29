package com.fs.swms.business.dto;

import lombok.Data;
@Data
public class QueryServiceRegisterManagement {
    private String serviceNo;//服务编号
    private String figureNo;
    private String boxNo;
    private String serviceType;//服务分类
    private String serviceLevel;//信息等级
    private String problemId;//问题分类表主键
    private String isShangxiu;//是否商修
    private String leaderApprovalStatus;//主管领导审批状态
    private String approvalStatus;//科长审批状态
    private String childSetp;//当前步骤
    private String childSetp1;//当前步骤
    private String childSetp2;//当前步骤
    private String executor;
    private String deptNo;
}
