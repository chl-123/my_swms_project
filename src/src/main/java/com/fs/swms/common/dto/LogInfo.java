package com.fs.swms.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
@Data
@ApiModel(value = "Log对象", description = "")
public class LogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "接口名称")
    private String methodName;

    @ApiModelProperty(value = "日志类型")
    private String logType;

    @ApiModelProperty(value = "入参")
    private String inParams;

    @ApiModelProperty(value = "出参")
    private String outParams;

    @ApiModelProperty(value = "操作名称")
    private String operationName;

    @ApiModelProperty(value = "操作的IP")
    private String operationIp;

    @ApiModelProperty(value = "记录日期")
    private Date createTime;

    @ApiModelProperty(value = "操作人")
    private String creator;
}
