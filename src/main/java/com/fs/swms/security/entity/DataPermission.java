package com.fs.swms.security.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fs.swms.common.entity.BaseEntity;
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
 * @author jeebase
 * @since 2019-02-27
 */
@Data
@TableName("t_sys_data_permission")
@ApiModel(value="DataPermission对象", description="")
public class DataPermission extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("organization_id")
    private String organizationId;
}
