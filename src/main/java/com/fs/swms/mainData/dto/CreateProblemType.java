package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author bai
 * @creat 2021-08-16-15:23
 */
@Data
public class CreateProblemType {
    private String id;

    private String typeName;

    private String parentId;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;


}
