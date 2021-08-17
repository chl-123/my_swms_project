package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class CustomerWindFarmInfo {
    private String id;

    private String customerName;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;
    private String delFlag;
    private List<WindFarmInfo> windFarmInfoList;
}
