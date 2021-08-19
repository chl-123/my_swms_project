package com.fs.swms.mainData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-08-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_BD_PRODUCT")
@ApiModel(value="Product对象", description="")
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "客户表ID")
    @TableField("CUSTOMER_ID")
    private String customerId;

    @ApiModelProperty(value = "产品图号")
    @TableField("FIGURE_NO")
    private String figureNo;

    @ApiModelProperty(value = "齿轮箱编号")
    @TableField("BOX_NO")
    private String boxNo;

    @ApiModelProperty(value = "功率")
    @TableField("POWER")
    private String power;

    @ApiModelProperty(value = "速比")
    @TableField("SPEED_RATIO")
    private String speedRatio;

    @ApiModelProperty(value = "装配日期")
    @TableField("ASSEMBLY_DATE")
    private Date assemblyDate;

    @ApiModelProperty(value = "出场日期")
    @TableField("MANUFACTURE_DATE")
    private Date manufactureDate;

    @ApiModelProperty(value = "厂内质量问题备注")
    @TableField("REMARKS")
    private String remarks;

    @ApiModelProperty(value = "附件名称")
    @TableField(value = "FILES")
    private String files;

    @ApiModelProperty(value = "合同号")
    @TableField("CONTRACT_NO")
    private String contractNo;

    @ApiModelProperty(value = "工号")
    @TableField("JOB_NO")
    private String jobNo;

    @ApiModelProperty(value = "安装风场ID")
    @TableField("WINDFARM_ID")
    private String windfarmId;

    @ApiModelProperty(value = "风机编号")
    @TableField("FAN_NO")
    private String fanNo;

    @ApiModelProperty(value = "并网时间")
    @TableField("CONN_DATE")
    private Date connDate;

    @ApiModelProperty(value = "出质保日期")
    @TableField("EXPIRE_DATE")
    private Date expireDate;


}
