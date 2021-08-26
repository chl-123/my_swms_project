package com.fs.swms.mainData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fs.swms.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_BD_WINDFARM")
@ApiModel(value="Windfarm对象", description="")
public class Windfarm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "客户表ID")
    @TableField("CUSTOMER_ID")
    private String customerId;

    @ApiModelProperty(value = "安装风场")
    @TableField("WINDFARM")
    private String windfarm;


}
