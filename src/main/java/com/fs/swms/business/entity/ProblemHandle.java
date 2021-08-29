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

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_MM_PROBLEM_HANDLE")
@ApiModel(value="ProblemHandle对象", description="")
public class ProblemHandle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "服务登记表ID")
    @TableField("APPROVAL_SHEET_ID")
    private String approvalSheetId;

    @ApiModelProperty(value = "责任部门编号")
    @TableField("DEPT_NO")
    private String deptNo;

    @ApiModelProperty(value = "转办人")
    @TableField("EXECUTOR")
    private String executor;

    @ApiModelProperty(value = "现场问题ID，多个问题使用”，“逗号分隔")
    @TableField("PROBLEM_ID")
    private String problemId;

    @ApiModelProperty(value = "处理方案")
    @TableField("PROCESS_SCHEME")
    private String processScheme;

    @ApiModelProperty(value = "附件地址")
    @TableField("FILENAMES")
    private String filenames;

    @ApiModelProperty(value = "当前步骤，0：新建（任务分发） 1：转办  2：问题处理完成")
    @TableField("CHILD_SETP")
    private String childSetp;


}
