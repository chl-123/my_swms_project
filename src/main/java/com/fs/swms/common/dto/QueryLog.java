package com.fs.swms.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
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
public class QueryLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接口名称")
    private String methodName;

    @ApiModelProperty(value = "日志类型")
    private String logType;

    @ApiModelProperty(value = "操作名称")
    private String operationName;

    @ApiModelProperty(value = "操作的IP")
    private String operationIp;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始日期")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束日期")
    private Date endTime;

    @ApiModelProperty(value = "操作人")
    private String creator;
}