package com.fs.swms.mainData.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateWindfarm implements Serializable {
    private static final long serialVersionUID = 1L;
//    private String id;
//
//    private String customerId;
//
//    private String windfarm;
//
//    private Date createTime;
//
//    private String creator;
//
//    private Date updateTime;
//
//    private String operator;
    private String customerName;
    private List<String> windFarmList;


}
