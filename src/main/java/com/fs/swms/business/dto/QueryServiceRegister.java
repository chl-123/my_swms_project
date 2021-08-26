package com.fs.swms.business.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class QueryServiceRegister {

    private String serviceNo;

    private String figureNo;

    private String boxNo;

    private String customerName;

    private String contractNo;

    private String jobNo;

    private String windfarmId;

    private String  windfarm;

    private String isFree;//是否无偿

    private String segments;//隶属

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureDateStart;//交货日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureDateEnd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDateStart;//出质保日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDateEnd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeEnd;

    private String approvalStatus;//审批表

    private String setp;//审批表

    private String childSetp;//服务登记表

    private String executor;

}
