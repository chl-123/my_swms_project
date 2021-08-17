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
public class ReadExcelWindfarm extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value="客户名称",index = 0)
    private String customerName;

    @ExcelProperty(value = "安装风场名称",index = 1)
    private String windfarm;
}
