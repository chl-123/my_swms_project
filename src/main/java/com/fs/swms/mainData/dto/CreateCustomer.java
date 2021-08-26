package com.fs.swms.mainData.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CreateCustomer implements Serializable {
    private String id;

    private String customerName;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;


}
