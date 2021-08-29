package com.fs.swms.security.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bai
 * @creat 2021-08-17-14:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadExcelUser extends BaseRowModel {
    @ExcelProperty(value="部门名称",index = 0)
    private String organizationName;

    @ExcelProperty(value = "账号",index = 1)
    private String userAccount;

    @ExcelProperty(value = "姓名",index = 2)
    private String userName;

    //@ExcelProperty(value = "角色",index = 3)
    //private String roleName;

    @ExcelProperty(value = "状态",index = 3)
    private String userStatus;

}
