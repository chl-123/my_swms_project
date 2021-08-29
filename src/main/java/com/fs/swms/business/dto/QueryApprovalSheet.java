package com.fs.swms.business.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class QueryApprovalSheet {

    private String serviceNo;

    private String customerName;

    private String figureNo;

    private String boxNo;

    private String contractNo;

    private String jobNo;

    private String windfarmId;

    private String  windfarm;

    private String isFree;//是否无偿

    private String segments;//隶属

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureDateEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDateEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeEnd;

    private String setp;

    private String creator;

    private String approvalStatus;//审批状态

    private String approvalResult;
}
