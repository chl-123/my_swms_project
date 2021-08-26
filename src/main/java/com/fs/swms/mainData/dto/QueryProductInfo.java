package com.fs.swms.mainData.dto;

import lombok.Data;

@Data
public class QueryProductInfo {

    private String customerName;

    private String figureNo;

    private String boxNo;

    private String assemblyDateStart;

    private String assemblyDateEnd;

    private String manufactureDateStart;

    private String manufactureDateEnd;

    private String windfarm;

    private String fanNo;

    private String connDateStart;

    private String connDateEnd;

    private String expireDateStart;

    private String expireDateEnd;

}
