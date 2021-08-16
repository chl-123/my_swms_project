package com.fs.swms.mainData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fs.swms.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_BD_CUSTOMER")
@ApiModel(value="Customer对象", description="")
public class Customer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("CUSTOMER_NAME")
    private String customerName;


}
