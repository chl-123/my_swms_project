package com.fs.swms.business.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bai
 * @creat 2021-08-23-20:12
 */
@Data
public class CreateProblemHandle {

    private String id;
    private Date createTime;
    private String creator;
    private Date updateTime;
    private String operator;
    private String delFlag;
    private String serviceRedId;//服务登记表ID
    private String deptNo;//责任部门编号
    private String executor;//转办人
    private String problemId;//现场问题ID
    private String processScheme;//处理方案
    private String filenames;//附件地址
    private String childSetp;//当前步骤


}
