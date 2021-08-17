package com.fs.swms.mainData.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadExcelSupplier extends BaseRowModel implements Serializable {
    @ExcelProperty(value="供应商代码",index = 0)
    private String supplierNo;

    @ExcelProperty(value = "供应商名称",index = 1)
    private String supplierName;
}
