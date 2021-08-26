package com.fs.swms.mainData.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CustomerWindFarmInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String customerName;
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    private String creator;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    private String operator;
    private String delFlag;

    private List<WindFarmInfo> windFarmInfoList;
}
