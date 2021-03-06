package com.fs.swms.common.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 工具类
 * @Date 2018-06-06
 * @Time 14:07
 */
public class ExcelUtil {
    public static <T extends BaseRowModel> List<T> read(MyFile file, Class<T> rowModel) throws Exception{

        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = getExcelReader(file.getFile(),excelListener,true);
        if(excelReader == null){
            return new ArrayList();
        }
        for(Sheet sheet:excelReader.getSheets()){
            sheet.setClazz(rowModel);
            excelReader.read(sheet);
        }
        List<T> list = new ArrayList<>();
        for(Object obj:excelListener.getDatas()){
            list.add((T)obj);
        }
        return list;
    }
    public static <T extends BaseRowModel> List<T> read(MyFile file, Class<T> rowModel, String sheetName
                                                        ) throws Exception{

        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = getExcelReader(file.getFile(),excelListener,true);
        if(excelReader == null){
            return new ArrayList();
        }
        Map<String,Integer> map=new HashMap<>();
        for(Sheet sheet:excelReader.getSheets()){
            if (sheet.getSheetName().equals(sheetName)) {
                map.put(sheetName,1);
                sheet.setClazz(rowModel);
                excelReader.read(sheet);
            }
        }
        if (map == null||map.size()==0) {
            throw new BusinessException("Excel表格中没有【"+sheetName+"】这个Excel表");
        }
        List<T> list = new ArrayList<>();
        for(Object obj:excelListener.getDatas()){
            list.add((T)obj);
        }
        return list;
    }

    public static <T extends BaseRowModel> List<String>  getSheetName(MyFile file, Class<T> rowModel
    ) throws Exception{

        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = getExcelReader(file.getFile(),excelListener,true);
        List<String> list = new ArrayList<>();
        for(Sheet sheet:excelReader.getSheets()){
            String sheetName = sheet.getSheetName();
            list.add(sheetName);
        }

        return list;
    }
    /**
     * 读取某个 sheet 的 Excel
     *
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, int sheetNo,
                                         int headLineNum) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        reader.read(new Sheet(sheetNo, headLineNum, rowModel.getClass()));
        return excelListener.getDatas();
    }
    /**
     *
     * @param file 文件输入流

     * @param eventListener 用户监听
     * @param trim 是否对解析的String做trim()默认true,用于防止excel中空格引起的装换报错
     * @return
     */
    public static ExcelReader getExcelReader(MultipartFile file,

                                             AnalysisEventListener eventListener, boolean trim) throws Exception{
//        String fileName  = file.getName();
        String fileName=file.getOriginalFilename();
        if (fileName == null ) {
            throw new Exception("文件格式错误！");
        }
        if (!fileName.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue()) && !fileName.toLowerCase().endsWith(ExcelTypeEnum.XLSX.getValue())) {
            throw new Exception("文件格式错误！");
        }
        InputStream inputStream = null;
        try{
//            inputStream = new FileInputStream(file);
            inputStream=file.getInputStream();
            if (fileName.toLowerCase().endsWith(ExcelTypeEnum.XLS.getValue())) {
                return new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, eventListener, false);
            } else {
                return new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, eventListener, false);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 读取 Excel(多个 sheet)
     *
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */

    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        for (Sheet sheet : reader.getSheets()) {
            if (rowModel != null) {
                sheet.setClazz(rowModel.getClass());
            }
            reader.read(sheet);
        }
        return excelListener.getDatas();
    }

    /**
     * 读取某个 sheet 的 Excel
     *
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @param sheetNo  sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, int sheetNo) {
        return readExcel(excel, rowModel, sheetNo, 1);
    }



    /**
     * 导出 Excel ：一个 sheet，带表头
     *
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                  String fileName, String sheetName, BaseRowModel object) {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        writer.finish();
    }

    /**
     * 导出 Excel ：多个 sheet，带表头
     *
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static ExcelWriterFactroy writeExcelWithSheets(HttpServletResponse response, List<? extends BaseRowModel> list,
                                                          String fileName, String sheetName, BaseRowModel object) {
        ExcelWriterFactroy writer = new ExcelWriterFactroy(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        return writer;
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    public static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        //创建本地文件
        String filePath = fileName + ".xlsx";
        File dbfFile = new File(filePath);
        try {
            if (!dbfFile.exists() || dbfFile.isDirectory()) {
                dbfFile.createNewFile();
            }
            fileName = new String(filePath.getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new ExcelException("创建文件失败！");
        }
    }

    /**
     * 返回 ExcelReader
     *
     * @param excel         需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     */
    private static ExcelReader getReader(MultipartFile excel,
                                         ExcelListener excelListener) {
        String filename = excel.getOriginalFilename();
        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            throw new ExcelException("文件格式错误！");
        }
        InputStream inputStream;
        try {
            inputStream = excel.getInputStream();
            return new ExcelReader(inputStream, null, excelListener, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 基本法导入excel，可以作为公共模板，传人参数headLineMun
     */
    public static List<Object> readExcelFile(File excelFile, BaseRowModel rowModel,int headLineMun) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excelFile, excelListener);
        if (reader == null) {
            return null;
        }
        for (Sheet sheet : reader.getSheets()) {
            if (rowModel != null) {
                sheet.setHeadLineMun(headLineMun);
                sheet.setClazz(rowModel.getClass());
            }
            reader.read(sheet);
        }
        return excelListener.getDatas();
    }

    /**
     * 返回 ExcelReader
     *
     * @param excelFile         需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     */
    private static ExcelReader getReader(File excelFile,
                                         ExcelListener excelListener) {
        String filename = excelFile.getName();
        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            throw new ExcelException("文件格式错误！");
        }

        try {
            FileInputStream  inputStream = new FileInputStream(excelFile);
            return new ExcelReader(inputStream, null, excelListener, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
