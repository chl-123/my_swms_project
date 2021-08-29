package com.fs.swms.business.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fs.swms.mainData.entity.ProblemType;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author bai
 * @creat 2021-08-23-20:12
 */
@Data
public class ProblemHandleInfoEntity {

    private String id;
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    private String creator;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;
    private String operator;
    private String delFlag;
    private String approvalSheetId;
    private String deptNo;//责任部门编号
    private String executor;//转办人
    private String problemId;//现场问题ID
    private String processScheme;//处理方案
    private String filenames;//附件地址
    private String childSetp;//当前步骤
    private List<ProblemType> problemList=new ArrayList<>();


}
