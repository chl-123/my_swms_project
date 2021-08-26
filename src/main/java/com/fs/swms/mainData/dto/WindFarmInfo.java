package com.fs.swms.mainData.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WindFarmInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String customerId;

    private String windfarm;
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    private String creator;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;

    private String operator;

    private String delFlag;
}
