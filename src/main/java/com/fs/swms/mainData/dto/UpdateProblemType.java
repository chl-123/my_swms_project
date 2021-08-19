package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bai
 * @creat 2021-08-16-15:48
 */
@Data
public class UpdateProblemType {
    private String id;

    private String typeName;

    private String parentId;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;
}
