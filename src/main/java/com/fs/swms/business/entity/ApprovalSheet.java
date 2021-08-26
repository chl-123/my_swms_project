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
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_MM_APPROVAL_SHEET")
@ApiModel(value="ApprovalSheet对象", description="")
public class ApprovalSheet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "齿轮箱主键")
    @TableField("PRODUCT_ID")
    private String productId;

    @ApiModelProperty(value = "现场地址")
    @TableField("ADDRESS")
    private String address;

    @ApiModelProperty(value = "联系人")
    @TableField("CONTACTS")
    private String contacts;

    @ApiModelProperty(value = "联系电话")
    @TableField("TEL")
    private String tel;

    @ApiModelProperty(value = "隶属板块，1：风电 2：工业")
    @TableField("SEGMENTS")
    private String segments;

    @ApiModelProperty(value = "是否无偿服务，0：否 1：是")
    @TableField("ISFREE")
    private String isFree;

    @ApiModelProperty(value = "问题描述")
    @TableField("PROBLEM_DESC")
    private String problemDesc;

    @ApiModelProperty(value = "附件地址")
    @TableField("FILENAMES")
    private String filenames;

    @ApiModelProperty(value = "审批状态，0：未审批 1:已审批")
    @TableField("APPROVAL_STATUS")
    private String approvalStatus;

    @ApiModelProperty(value = "审批结果，0：未审批 1：通过 2：拒绝")
    @TableField("APPROVAL_RESULT")
    private String approvalResult;

    @ApiModelProperty(value = "审批意见")
    @TableField("APPROVAL_COMMENTS")
    private String approvalComments;

    @ApiModelProperty(value = "审批人")
    @TableField("APPROVAER")
    private String approvaer;

    @ApiModelProperty(value = "审批时间")
    @TableField("APPROVAL_DATE")
    private Date approvalDate;

    @ApiModelProperty(value = "当前步骤，1：创建审批单 2：服务登记 3：现场问题处理 4：质管定责 5：问题整改 6：质管确认 7：服务确认 8：财务确认 9：完成闭环")
    @TableField("SETP")
    private String setp;


}
