package com.fs.swms.mainData.entity;

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
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_BD_PROBLEM_TYPE")
@ApiModel(value="ProblemType对象", description="")
public class ProblemType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "问题类型名称")
    @TableField("TYPE_NAME")
    private String typeName;

    @ApiModelProperty(value = "上一级问题类型名称")
    @TableField("PARENT_ID")
    private String parentId;

   /* @ApiModelProperty(value = "上一级ID，无上一级则为0")
    @TableField("PARENT_ID")
    private String parentId;*/


}
