package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.Date;
@Data
public class WindFarmInfo {
    private String id;

    private String customerId;

    private String windfarm;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;
    private String delFlag;
}
