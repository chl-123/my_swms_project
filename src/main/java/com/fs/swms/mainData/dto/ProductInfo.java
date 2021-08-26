package com.fs.swms.mainData.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String customerId;

    private String customerName;

    private String figureNo;

    private String boxNo;

    private String power;

    private String speedRatio;
    @JSONField(format = "yyyy-MM-dd")
    private Date assemblyDate;

    @JSONField(format = "yyyy-MM-dd")
    private Date   manufactureDate;

    private String remarks;

    private String files;

    private String contractNo;

    private String jobNo;

    private String windfarmId;

    private String windfarm;

    private String fanNo;

    @JSONField(format = "yyyy-MM-dd")
    private Date   connDate;

    @JSONField(format = "yyyy-MM-dd")
    private Date   expireDate;

    @JSONField(format = "yyyy-MM-dd")
    private Date   createTime;

    private String creator;

    @JSONField(format = "yyyy-MM-dd")
    private Date   updateTime;

    private String operator;
}
