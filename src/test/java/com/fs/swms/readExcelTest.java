package com.fs.swms;

import com.fs.swms.mainData.dto.ReadExcelSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class readExcelTest {
    public static void main(String[] args)  {
        String path="C:\\Users\\chl\\Desktop\\test.xlsx";
        List<ReadExcelSupplier> testList=new ArrayList<ReadExcelSupplier>();
        ReadExcelSupplier supplier1 =new ReadExcelSupplier("221","111");
        ReadExcelSupplier supplier2 =new ReadExcelSupplier("222","111");
        ReadExcelSupplier supplier3 =new ReadExcelSupplier("222","111");
        testList.add(supplier1);
        testList.add(supplier2);
        testList.add(supplier3);
        Map<String, Integer> map = new HashMap<>();
        for(ReadExcelSupplier s : testList){
            //1:map.containsKey()   检测key是否重复
            if(map.containsKey(s.getSupplierNo())){
                System.out.println("有重复");
                Integer num = map.get(s.getSupplierNo());
                map.put(s.getSupplierNo(), num+1);
            }else{
                map.put(s.getSupplierNo(), 1);
            }

    }



//        try {
//            List<ReadExcelSupplier> users = ExcelUtil.read(path, ReadExcelSupplier.class);
//            System.out.println(users);
//            System.out.println("读取完成");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

}
