package com.fs.swms.business.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class QueryProblemHandle {
    private String id;
    private String approvalSheetId;//审批单表主键
    private String approvalStatus;//科长审批状态
    private String executor;//转办人
    private String executorName;
    private String serviceNo;//服务编
    private String childSetp;//当前步骤
    private String childSetp1;//当前步骤

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeEnd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeEnd;
    private String delFlag;

    private String figureNo;
    private String boxNo;

    private String customerName;
    private String deptNo;

    private String operator;

    private String problemId;//服务等表的problemID

    private String problemIds;//问题处理表的problemID

    private String setp;
    private String serviceType;//服务分类
    private String serviceLevel;//信息等级
    private String isShangxiu;//是否商修



}
