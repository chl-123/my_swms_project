package com.fs.swms.mainData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.excel.ExcelUtil;
import com.fs.swms.common.util.Utils;
import com.fs.swms.mainData.dto.*;
import com.fs.swms.mainData.entity.Customer;
import com.fs.swms.mainData.entity.Windfarm;
import com.fs.swms.mainData.mapper.WindfarmMapper;
import com.fs.swms.mainData.service.ICustomerService;
import com.fs.swms.mainData.service.IWindfarmService;
import com.fs.swms.security.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2021-08-16
 */
@Service
public class WindfarmServiceImpl extends ServiceImpl<WindfarmMapper, Windfarm> implements IWindfarmService {
    @Autowired
    private WindfarmMapper windfarmMapper;
    @Autowired
    private ICustomerService iCustomerService;


    @Override
    public boolean createWindfarm(CreateWindfarm windfarm) {
        Customer customer=new Customer();
        customer.setCustomerName(windfarm.getCustomerName());
        //查询并判断是否重复
        List<Customer> customerList=iCustomerService.selectCustomers(customer.getCustomerName());

        if(!CollectionUtils.isEmpty(customerList)){
            throw new BusinessException("客户已存在");
        }
        //保存客户信息并且将信息返回
        Customer customer1 = iCustomerService.insertCustomer(customer);
        Collection<Windfarm> windfarmEntityList=new ArrayList<>();
        for (String windfarmName:windfarm.getWindFarmList()) {//遍历拷贝属性
            Windfarm windfarm1=new Windfarm();
            windfarm1.setCustomerId(customer1.getId());
            windfarm1.setWindfarm(windfarmName);
            windfarmEntityList.add(windfarm1);
        }
        //执行保存
        boolean result = this.saveBatch(windfarmEntityList);

        return result;
    }

    @Override
    public boolean updateWindfarm(UpdateCustomer windfarm) {
        boolean result=false;
        //查询数据库并判断客户名称是否重复
        QueryWrapper<Customer> ew=new QueryWrapper<>();
        ew.eq("ID",windfarm.getId()).or().eq("CUSTOMER_NAME",windfarm.getCustomerName());
        List<Customer> customerList = iCustomerService.list(ew);
        if (customerList.size()>1) {
            throw new BusinessException("客户名称已存在");
        }
        Customer customer=new Customer();
        customer.setCustomerName(windfarm.getCustomerName());
        result = iCustomerService.update(customer, ew);
        QueryWrapper<Windfarm> windfarmQueryWrapper=new QueryWrapper<>();
        windfarmQueryWrapper.eq("CUSTOMER_ID", windfarm.getId());
        List<Windfarm> windFarmList = this.list(windfarmQueryWrapper);
        //将前端传过来的风场ID和数据库查询的ID 分别取出
        List<String> tmWindFarmIds=new ArrayList<>();
        List<String> windFarmIds=new ArrayList<>();

        for(UpdateWindfarm updateWindfarm:windfarm.getTmpWindFarmList()){
            tmWindFarmIds.add(updateWindfarm.getId());
        }
        for(Windfarm windfarm1:windFarmList){
            windFarmIds.add(windfarm1.getId());
        }
        //遍历将前端没有的ID，在数据库里执行删除
        for(Windfarm windfarm1:windFarmList){
            if (!tmWindFarmIds.contains(windfarm1.getId())) {
                result = this.removeById(windfarm1.getId());
            }
        }
        //执行更新
        for(UpdateWindfarm updateWindfarm:windfarm.getTmpWindFarmList()){
            Windfarm insertWindFarm=new Windfarm();
            BeanUtils.copyProperties(updateWindfarm,insertWindFarm);
            if (!windFarmIds.contains(updateWindfarm.getId())) {
                insertWindFarm.setCustomerId(windfarm.getId());
                result = this.save(insertWindFarm);
            }
            else {
                QueryWrapper<Windfarm> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("ID",updateWindfarm.getId());
                result=this.update(insertWindFarm,queryWrapper);
            }
        }

        return result;
    }

    @Override
    public boolean deleteWindfarm(String customerId) {
        boolean result=false;
        result=iCustomerService.deleteCustomer(customerId);
        result=windfarmMapper.deleteByCustomerId(customerId);
        return result;

    }

    @Override
    public boolean batchCreateWindfarm(MyFile file, User user) throws Exception {
        if (file.getFile()==null) {
            throw new BusinessException("文件不能为空，请选择文件上传");
        }
        Map<String, Integer> map1 = new HashMap<>();

        boolean result=false;
        //读取Excel表格获取数据
        String sheetName="客户风场信息_营销科";
        List<ReadExcelWindfarm> dataList = ExcelUtil.read(file, ReadExcelWindfarm.class,sheetName);
        if (dataList.size()==0) {

            throw new BusinessException("基础数据模板中【"+sheetName+"】这个Excel表没有数据");
        }
        for(ReadExcelWindfarm info:dataList){//检查表格中的数据是否合法
            if (info.getCustomerName()==null) {
                throw new BusinessException("客户名称不能为空");
            }
            if (info.getWindfarm()==null) {
                throw new BusinessException("风场名称不能为空");
            }
            //1:map.containsKey()   检测key是否重复
            if ((map1.containsKey(info.getCustomerName()))&&map1.containsKey(info.getWindfarm())) {
                map1.clear();
                throw new BusinessException("文档中客户名称【"+info.getCustomerName()+"】风场名称【"+info.getWindfarm()+"】有重复");

            } else {
                map1.put(info.getCustomerName(), 1);
                map1.put(info.getWindfarm(), 1);
            }
        }
        for(ReadExcelWindfarm info:dataList){
            //查询从数据库中中查数据
            QueryWrapper<Windfarm> windfarmQueryWrapper=new QueryWrapper<>();
            windfarmQueryWrapper.eq("WINDFARM",info.getWindfarm());
            List<Windfarm> windfarmList=this.list(windfarmQueryWrapper);
            List<Customer> customerList=iCustomerService.selectCustomers(info.getCustomerName());
            //判断是否重复
            if(!CollectionUtils.isEmpty(windfarmList)&&!CollectionUtils.isEmpty(customerList)){
                throw new BusinessException("文档中客户名称【"+info.getCustomerName()+"】风场名称【"+info.getWindfarm()+"】有重复");
            }
        }
        //将Excel中的数据分类
        String [][] data =new String[dataList.size()][dataList.size()];
        Map<String,Integer> map2=new HashMap<>();
        Integer i=0;
        Integer j=0;
        for (ReadExcelWindfarm info:dataList){
            if (map2.containsKey(info.getCustomerName())) {
                data[map2.get(info.getCustomerName())][j++]=info.getWindfarm();
            } else {
                map2.put(info.getCustomerName(), i);
                data[i++][j++]=info.getWindfarm();
            }
        }
        //执行保存
        for (int k = 0; k <data.length ; k++) {
            Collection<Windfarm> windfarmEntityList=new ArrayList<>();
            for (int l = 0; l <data.length ; l++) {
                if (data[k][l]!=null) {
                    Windfarm windfarm=new Windfarm();
                    windfarm.setWindfarm(data[k][l]);
                    windfarmEntityList.add(windfarm);
                }
            }
            String customerName = Utils.getKeyByValue(map2, k);
            if ( !CollectionUtils.isEmpty(windfarmEntityList)&& customerName!=null) {
                Customer customer=new Customer();
                customer.setCustomerName(customerName);
//                result=iCustomerService.save(customer);
//                QueryWrapper<>
                customer.setCreator(user.getId());
                customer.setCreateTime(new Date());
                Customer customer1 = iCustomerService.insertCustomer(customer);
                for (Windfarm windfarm:windfarmEntityList) {
                    windfarm.setCustomerId(customer1.getId());
                }
                 result = this.saveBatch(windfarmEntityList);
            }
        }

        return result;
    }

    @Override
    public QueryWindfarm selectWindfarmByCustomerId(String customerId) {
        Customer customer = iCustomerService.getById(customerId);
        if (customer == null) {
            throw new BusinessException("根据该客户ID查询不到结果");
        }
        QueryWrapper<Windfarm> windfarmQueryWrapper=new QueryWrapper<>();
        windfarmQueryWrapper.eq("CUSTOMER_ID",customerId);
        List<Windfarm> windfarmList=this.list(windfarmQueryWrapper);
        QueryWindfarm queryWindfarm=new QueryWindfarm();
        queryWindfarm.setCustomerName(customer.getCustomerName());
        List<Windfarm> windfarms=new ArrayList<>();
        for (Windfarm windfarm: windfarmList){
            Windfarm windfarmEntity=new Windfarm();

            BeanUtils.copyProperties(windfarm,windfarmEntity);
            windfarms.add(windfarmEntity);
        }
        BeanUtils.copyProperties(customer,queryWindfarm);
        queryWindfarm.setWindFarmInfoList(windfarms);

        return queryWindfarm;
    }


}
