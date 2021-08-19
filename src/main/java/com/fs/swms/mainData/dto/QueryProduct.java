package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.Date;

@Data
public class QueryProduct {

    private String customerName;

    private String figureNo;

    private String boxNo;

    private Date assemblyDateStart;

    private Date assemblyDateEnd;

    private Date manufactureDateStart;

    private Date manufactureDateEnd;

    private String windfarm;

    private String fanNo;

    private Date connDateStart;

    private Date connDateEnd;

    private Date expireDateStart;

    private Date expireDateEnd;

}
