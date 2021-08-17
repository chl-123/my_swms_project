package com.fs.swms.mainData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public Page<Windfarm> selectWindfarmList(Page<Windfarm> page, Windfarm windfarm) {
        return null;
    }

    @Override
    public Page<Windfarm> selectWindfarmAll(Page<Windfarm> page) {
        return null;
    }

    @Override
    public boolean createWindfarm(CreateWindfarm windfarm) {
        Customer customer=new Customer();
        customer.setCustomerName(windfarm.getCustomerName());
        List<Customer> customerList=iCustomerService.selectCustomers(customer.getCustomerName());
        //判断是否重复
        if(!CollectionUtils.isEmpty(customerList)){
            throw new BusinessException("客户已存在");
        }
        Customer customer1 = iCustomerService.insertCustomer(customer);
        Collection<Windfarm> windfarmEntityList=new ArrayList<>();

        for (String windfarmName:windfarm.getWindFarmList()) {
            Windfarm windfarm1=new Windfarm();
            windfarm1.setCustomerId(customer1.getId());
            windfarm1.setWindfarm(windfarmName);
            windfarmEntityList.add(windfarm1);
        }
        boolean result = this.saveBatch(windfarmEntityList);

        return result;
    }

    @Override
    public boolean updateWindfarm(UpdateCustomer windfarm) {
        boolean result=false;
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

        List<String> tmWindFarmIds=new ArrayList<>();
        List<String> windFarmIds=new ArrayList<>();

        for(UpdateWindfarm updateWindfarm:windfarm.getTmpWindFarmList()){
            tmWindFarmIds.add(updateWindfarm.getId());
        }
        for(Windfarm windfarm1:windFarmList){
            windFarmIds.add(windfarm1.getId());
        }

        for(Windfarm windfarm1:windFarmList){
            if (!tmWindFarmIds.contains(windfarm1.getId())) {
                result = this.removeById(windfarm1.getId());
            }
        }
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
    public boolean deleteWindfarm(String customerId,String windfarmId) {
        QueryWrapper<Windfarm> ew=new QueryWrapper<>();
        ew.eq("CUSTOMER_ID",customerId);
        List<Windfarm> list = this.list(ew);
        if (list.size()==1&&!CollectionUtils.isEmpty(list)) {
            return (iCustomerService.deleteCustomer(customerId)&&this.removeById(windfarmId));
        }else{
            boolean result = this.removeById(windfarmId);
            return result;
        }

    }

    @Override
    public boolean batchCreateWindfarm(MyFile file) throws Exception {
        Map<String, Integer> map1 = new HashMap<>();

        boolean result=false;
        //读取Excel表格获取数据
        List<ReadExcelWindfarm> dataList = ExcelUtil.read(file, ReadExcelWindfarm.class);
        for(ReadExcelWindfarm info:dataList){
            if (info.getCustomerName()==null) {
                throw new BusinessException("客户名称不能为空");
            }
            if (info.getWindfarm()==null) {
                throw new BusinessException("风场名称不能为空");
            }
            //1:map.containsKey()   检测key是否重复
            if ((map1.containsKey(info.getCustomerName()))&&map1.containsKey(info.getWindfarm())) {
                map1.clear();
                throw new BusinessException("文档中客户名称"+info.getCustomerName()+"风场名称"+info.getWindfarm()+"有重复");

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
                throw new BusinessException("文档中客户名称"+info.getCustomerName()+"风场名称"+info.getWindfarm()+"有重复");
            }
        }

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
        QueryWrapper<Windfarm> windfarmQueryWrapper=new QueryWrapper<>();
        windfarmQueryWrapper.eq("CUSTOMER_ID",customerId);
        List<Windfarm> windfarmList=this.list(windfarmQueryWrapper);
        QueryWindfarm queryWindfarm=new QueryWindfarm();
        queryWindfarm.setCustomerName(customer.getCustomerName());
        List<String> windfarmNameList=new ArrayList<>();
        for (Windfarm windfarm: windfarmList){
            windfarmNameList.add(windfarm.getWindfarm());
        }
        queryWindfarm.setWindFarmList(windfarmNameList);
        return queryWindfarm;
    }
}