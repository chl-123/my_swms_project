package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.List;
@Data
public class QueryWindfarm {
    private String customerName;
    private List<String> windFarmList;
}
