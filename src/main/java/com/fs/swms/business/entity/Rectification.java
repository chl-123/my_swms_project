package com.fs.swms.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fs.swms.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_MM_RECTIFICATION")
@ApiModel(value="Rectification对象", description="")
public class Rectification extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "服务登记表ID")
    @TableField("SERVICE_RED_ID")
    private String serviceRedId;

    @ApiModelProperty(value = "整改部门编号")
    @TableField("DEPT_NO")
    private String deptNo;

    @ApiModelProperty(value = "转办人")
    @TableField("ESECUTOR")
    private String esecutor;

    @ApiModelProperty(value = "原因分析")
    @TableField("ANALYSIS_CONTENT")
    private String analysisContent;

    @ApiModelProperty(value = "原因分析附件名称")
    @TableField("ANALYSIS_FILENAMES")
    private String analysisFilenames;

    @ApiModelProperty(value = "整改措施")
    @TableField("RECTIFY_CONTENT")
    private String rectifyContent;

    @ApiModelProperty(value = "整改措施附件名称")
    @TableField("RECTIFY_FILENAMES")
    private String rectifyFilenames;

    @ApiModelProperty(value = "审批状态，0：未审批 1：通过 2：拒绝")
    @TableField("APPROVAL_STATUS")
    private String approvalStatus;

    @ApiModelProperty(value = "审批意见")
    @TableField("APPROVAL_COMMENTS")
    private String approvalComments;

    @ApiModelProperty(value = "审批人")
    @TableField("APPROVAER")
    private String approvaer;

    @ApiModelProperty(value = "审批时间")
    @TableField("APPROVAL_DATE")
    private Date approvalDate;

    @ApiModelProperty(value = "当前步骤，0：新建（质管定责整改） 1：整改完成 2：审批")
    @TableField("CHILD_SETP")
    private String childSetp;


}
