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
public class ReadExcelOrganization extends BaseRowModel {
    @ExcelProperty(value="部门名称",index = 0)
    private String organizationName;

    @ExcelProperty(value = "部门标识",index = 1)
    private String organizationKey;

    @ExcelProperty(value = "部门排序",index = 2)
    private String organizationLevel;

}
