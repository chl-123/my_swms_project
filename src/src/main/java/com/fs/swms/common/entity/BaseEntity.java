package com.fs.swms.common.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fs.swms.common.component.MyLocalDateTimeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(value = "create_time", fill = INSERT)
    private Date createTime;

    @TableField(value = "creator", fill = INSERT)
    private String creator;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(value = "operator", fill = FieldFill.UPDATE)
    private String operator;

    @ApiModelProperty(value = "1:删除 0:不删除")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;
}

