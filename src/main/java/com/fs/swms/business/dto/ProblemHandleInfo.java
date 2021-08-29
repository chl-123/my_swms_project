package com.fs.swms.business.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fs.swms.mainData.entity.ProblemType;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ProblemHandleInfo {
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
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;
    private String creator;
    private String creatorName;
    private String disposalMeasures;//处置措施

    private String childSetp;//当前步骤


    private String delFlag;

    private String figureNo;
    private String boxNo;

    private String customerName;
    private String organizationName;
    private String deptNo;
    private String problemDesc;
    private String operator;
    private String operatorName;
    private String problemIds;
    private List<ProblemType> problemList=new ArrayList<>();

}
