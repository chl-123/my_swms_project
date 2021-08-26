package com.fs.swms.mainData.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CreateProduct {

    private String id;

    private String customerId;

    private String figureNo;

    private String boxNo;

    private String power;

    private String speedRatio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date assemblyDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureDate;

    private String remarks;

    private String baseFiles;

    private String files;

    private String contractNo;

    private String jobNo;

    private String windfarmId;

    private String fanNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date connDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String creator;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private String operator;
}
