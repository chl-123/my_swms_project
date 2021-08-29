package com.fs.swms.mainData.dto;

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
public class ReadExcelProblemType extends BaseRowModel {
    @ExcelProperty(value="问题类型名称",index = 0)
    private String typeName;

    @ExcelProperty(value = "上一级问题类型名称",index = 1)
    private String parentTypeName;

}
