package com.fs.swms.mainData.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_BD_SUPPLIER")
@ApiModel(value="Supplier对象", description="")
public class Supplier extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value="ID" ,type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "供应商代码")
    @TableField("SUPPLIER_NO")
    private String supplierNo;

    @ApiModelProperty(value = "供应商名称")
    @TableField("SUPPLIER_NAME")
    private String supplierName;


}
