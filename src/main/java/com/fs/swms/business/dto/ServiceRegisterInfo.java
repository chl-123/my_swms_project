package com.fs.swms.business.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
@Data
public class ServiceRegisterInfo {
    private String id;
    private String approvalSheetId;//审批单表主键

    private String executor;//转办人
    private String executorName;
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
    private String approvaerName;
    @JSONField(format = "yyyy-MM-dd")
    private Date approvalDate;//科长审批时间
    private String leaderApprovalStatus;//主管领导审批状态
    private String leaderApprovalComments;//主管领导审批意见
    private String leaderApprovaer;//主管领导审批人
    private String leaderApprovaerName;
    @JSONField(format = "yyyy-MM-dd")
    private String leaderApprovalDate;//主管领导审批时间
    private String childSetp;//当前步骤
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    private String creator;
    private String creatorName;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;
    private String operator;
    private String operatorName;
    private String delFlag;

    private String figureNo;
    private String boxNo;

    private String customerName;

    private String contractNo;

    private String jobNo;

    private String windfarmId;

    private String  windfarm;

    private String isFree;//是否无偿

    private String segments;//隶属

    @JSONField(format = "yyyy-MM-dd")
    private Date   manufactureDate;//出厂日期
    @JSONField(format = "yyyy-MM-dd")
    private Date expireDate;  //质保日期

    private String deptNo;
    private String organizationName;

    private String problemDesc;

}
