package com.fs.swms.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bai
 * @creat 2021-08-23-20:13
 */
@Data
public class UpdateProblemHandle {
    private String id;
    private Date createTime;
    private String creator;
    private Date updateTime;
    private String operator;
    private String delFlag;
    private String approvalSheetId;
    private String deptNo;//责任部门编号
    private String executor;//转办人
    private String problemId;//现场问题ID
    private String processScheme;//处理方案
    private String filenames;//附件地址
    private String baseFiles;
    private String childSetp;//当前步骤
}
