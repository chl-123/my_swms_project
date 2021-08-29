package com.fs.swms.mainData.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author bai
 * @creat 2021-08-16-15:23
 */
@Data
public class ProblemTypeInfo {
    private String id;

    private String typeName;

    private String parentId;

    private String parentTypeName;
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    private String creator;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    private String operator;


}
