package com.fs.swms.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bai
 * @creat 2021-08-19-21:41
 */
@Data
public class CreateRectification {

    private String id;
    private Date createTime;
    private String creator;
    private Date updateTime;
    private String operator;
    private String delFlag;
    private String serviceRedId;
    private String deptNo;
    private String esecutor;
    private String analysisContent;
    private String analysisFilenames;
    private String rectifyFilenames;
    private String rectifyContent;
    private String approvalStatus;
    private String approvalComments;
    private String approvaer;
    private String approvalDate;
    private String childSetp;
}
