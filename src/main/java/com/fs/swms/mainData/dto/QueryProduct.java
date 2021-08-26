package com.fs.swms.mainData.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class QueryProduct {

    private String customerName;

    private String figureNo;

    private String boxNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date assemblyDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date assemblyDateEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date manufactureDateEnd;

    private String windfarm;

    private String fanNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date connDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date connDateEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDateEnd;

}
