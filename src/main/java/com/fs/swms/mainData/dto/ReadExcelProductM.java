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
public class ReadExcelProductM extends BaseRowModel implements Serializable {
    @ExcelProperty(value="客户名称",index = 0)
    private String customerName;
    @ExcelProperty(value="产品图号",index = 1)
    private String figureNo;
    @ExcelProperty(value="齿轮箱编号",index = 2)
    private String boxNo;
    @ExcelProperty(value="合同号",index = 3)
    private String contractNo;
    @ExcelProperty(value="工号",index = 4)
    private String jobNo;
    @ExcelProperty(value="安装风场名称",index = 5)
    private String windfarm;
    @ExcelProperty(value="风机编号",index = 6)
    private String fanNo;
    @ExcelProperty(value="并网时间",index = 7)
    private String connDate;
    @ExcelProperty(value="出质保日期",index = 8)
    private String expireDate;

}
