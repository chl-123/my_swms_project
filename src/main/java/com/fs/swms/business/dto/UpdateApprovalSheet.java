package com.fs.swms.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bai
 * @creat 2021-08-18-21:26
 */
@Data
public class UpdateApprovalSheet {
    private String id;
    private String boxNo;//齿轮箱主键
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
    private Date   approvalDate;//审批时间
    private String setp;//当前步骤
    private String filenames;//附件地址
    private String baseFiles;
    private Date   createTime;
    private String creator;
    private Date   updateTime;
    private String operator;

}
