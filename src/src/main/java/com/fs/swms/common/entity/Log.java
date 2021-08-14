package com.fs.swms.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 */
@Data
@TableName("t_sys_log")
@ApiModel(value = "Log对象", description = "")
public class Log extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "接口名称")
    @TableField("method_name")
    private String methodName;

    @ApiModelProperty(value = "入参")
    @TableField("in_params")
    private String inParams;

    @ApiModelProperty(value = "出参")
    @TableField("out_params")
    private String outParams;

    @ApiModelProperty(value = "日志类型")
    @TableField("log_type")
    private String logType;

    @ApiModelProperty(value = "操作名称")
    @TableField("operation_name")
    private String operationName;

    @ApiModelProperty(value = "操作的IP")
    @TableField("operation_ip")
    private String operationIp;
}