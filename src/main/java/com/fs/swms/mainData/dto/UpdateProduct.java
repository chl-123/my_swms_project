package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateProduct {

    private String id;

    private String customerId;

    private String figureNo;

    private String boxNo;

    private String power;

    private String speedRatio;

    private Date assemblyDate;

    private Date manufactureDate;

    private String remarks;

    private String files;

    private String contractNo;

    private String jobNo;

    private String windfarmId;

    private String fanNo;

    private Date connDate;

    private Date expireDate;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;

}
