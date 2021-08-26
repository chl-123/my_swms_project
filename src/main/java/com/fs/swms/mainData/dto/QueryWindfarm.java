package com.fs.swms.mainData.dto;

import com.fs.swms.mainData.entity.Windfarm;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QueryWindfarm {
    private String id;

    private String customerName;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String operator;
    private List<Windfarm> windFarmInfoList;
}
