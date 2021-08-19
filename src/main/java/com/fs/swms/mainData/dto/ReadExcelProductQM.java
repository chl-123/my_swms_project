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
public class ReadExcelProductQM extends BaseRowModel implements Serializable {
    @ExcelProperty(value="客户名称",index = 0)
    private String customerName;
    @ExcelProperty(value="产品图号",index = 1)
    private String figureNo;
    @ExcelProperty(value="齿轮箱编号",index = 2)
    private String boxNo;
    @ExcelProperty(value="功率",index = 3)
    private String power;
    @ExcelProperty(value="速比",index = 4)
    private String speedRatio;
    @ExcelProperty(value="装配日期",index = 5)
    private String assemblyDate;
    @ExcelProperty(value="出厂日期",index = 6)
    private String manufactureDate;
    @ExcelProperty(value="厂内质量问题备注",index = 7)
    private String remarks;

}
