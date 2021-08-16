package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.Date;
@Data
public class UpdateSupplier {
    private String id;

    private String supplierNo;

    private String supplierName;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;
}
