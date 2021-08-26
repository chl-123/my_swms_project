package com.fs.swms.mainData.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CreateSupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String supplierNo;

    private String supplierName;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;
}
