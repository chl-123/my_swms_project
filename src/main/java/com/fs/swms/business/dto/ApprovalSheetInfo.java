package com.fs.swms.business.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ApprovalSheetInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String serviceNo;

    private String customerId;

    private String customerName;

    private String windfarmId;

    private String windfarm;

    private String productId;

    private String figureNo;

    private String boxNo;

    private String contractNo;//合同号

    private String jobNo;

    private String power;

    private String speedRatio;
    @JSONField(format = "yyyy-MM-dd")
    private Date   assemblyDate;//装配日期
    @JSONField(format = "yyyy-MM-dd")
    private Date   manufactureDate;//出厂日期
    @JSONField(format = "yyyy-MM-dd")
    private Date expireDate;  //质保日期

    private String address;//现场地址
    private String contacts;//联系人
    private String tel;
    private String segments;//隶属
    private String isFree;//是否无偿
    private String problemDesc;//问题描述
    private String approvalStatus;//审批状态
    private String approvalResult;
    private String approvalComments;//审批意见
    private String approvaer;//审批人
    private String approvaerName;
    @JSONField(format = "yyyy-MM-dd")
    private Date   approvalDate;//审批时间
    private String setp;//当前步骤
    private String filenames;//附件地址
    @JSONField(format = "yyyy-MM-dd")
    private Date   createTime;
    private String creator;
    private String creatorName;
    @JSONField(format = "yyyy-MM-dd")
    private Date   updateTime;
    private String operator;
    private String operatorName;
}
