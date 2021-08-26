package com.fs.swms.mainData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.excel.ExcelUtil;
import com.fs.swms.mainData.dto.CreateSupplier;
import com.fs.swms.mainData.dto.ReadExcelSupplier;
import com.fs.swms.mainData.dto.UpdateSupplier;
import com.fs.swms.mainData.entity.Supplier;
import com.fs.swms.mainData.mapper.SupplierMapper;
import com.fs.swms.mainData.service.ISupplierService;
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
 * @since 2021-08-14
 */

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements ISupplierService {
    @Autowired
    SupplierMapper supplierMapper;
    @Override
    public Page<Supplier> selectSupplierList(Page<Supplier> page, Supplier supplier) {
        Page<Supplier> supplierPage = supplierMapper.selectSupplierList(page, supplier);
        return supplierPage;
    }

    @Override
    public Page<Supplier> selectSupplierAll(Page<Supplier> page) {
        QueryWrapper<Supplier> supplierQueryWrapper=new QueryWrapper<>();
        Page<Supplier> supplierPage = this.page(page, supplierQueryWrapper);
        return supplierPage;
    }

    @Override
    public boolean createSupplier(CreateSupplier supplier) {
        QueryWrapper<Supplier> ew=new QueryWrapper<>();
        //查找相应的条件
        ew.eq("SUPPLIER_NAME",supplier.getSupplierName()).or().eq("SUPPLIER_NO",supplier.getSupplierNo());
        List<Supplier> supplierList=this.list(ew);
        if(!CollectionUtils.isEmpty(supplierList)){//判断是否重复
            for(Supplier supplier1:supplierList){
                if (supplier1.getSupplierNo().equals(supplier.getSupplierNo())) {
                    throw new BusinessException("供应商代码已存在");
                }
                if(supplier1.getSupplierName().equals(supplier.getSupplierName())){
                    throw new BusinessException("供应商名称已存在");
                }
            }
        }
        //拷贝数据
        Supplier supplierEntity=new Supplier();
        BeanUtils.copyProperties(supplier,supplierEntity);
        //执行保存
        boolean result = this.save(supplierEntity);
        return result;

    }

    @Override
    public boolean updateSupplier(UpdateSupplier supplier) {
        //查询从数据库中中查数据
        QueryWrapper<Supplier> ew=new QueryWrapper<>();
        ew.ne("SUPPLIER_NO", supplier.getSupplierNo()).and(e->e.eq("SUPPLIER_NAME",supplier.getSupplierName()));
        List<Supplier> supplierList=this.list(ew);
        if(!CollectionUtils.isEmpty(supplierList)){//判断是否重复
            throw new BusinessException("供应商名称已存在");
        }
        //拷贝数据
        Supplier supplierEntity=new Supplier();
        BeanUtils.copyProperties(supplier,supplierEntity);
        //执行保存
        QueryWrapper<Supplier> wrapper=new QueryWrapper<>();
        wrapper.eq("SUPPLIER_NO", supplier.getSupplierNo());
        boolean result = this.update(supplierEntity,wrapper);
        return result;
    }

    @Override
    public boolean deleteSupplier(String supplierNo) {
        boolean result = supplierMapper.deleteBySupplierNo(supplierNo);
        return result;
    }

    @Override
    public boolean batchCreateSupplier(MyFile file) throws Exception {
        if (file.getFile()==null) {
            throw new BusinessException("文件不能为空，请选择文件上传");
        }
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        //读取Excel表格获取数据
        List<ReadExcelSupplier> dataList = ExcelUtil.read(file, ReadExcelSupplier.class);
        for(ReadExcelSupplier supplier:dataList){
            if (supplier.getSupplierNo()==null) {
                throw new BusinessException("供应商代码不能为空");
            }
            //1:map.containsKey()   检测key是否重复
            if (map1.containsKey(supplier.getSupplierNo())) {
                map1.clear();
                throw new BusinessException("供应商代码重复");
            } else {
                map1.put(supplier.getSupplierNo(), 1);
            }
            if (supplier.getSupplierName()==null) {
                throw new BusinessException("供应商名称不能为空");
            }
            if (map2.containsKey(supplier.getSupplierName())) {
                map2.clear();
                throw new BusinessException("供应商名称重复");
            } else {
                map2.put(supplier.getSupplierName(), 1);
            }
        }
        for(ReadExcelSupplier supplier:dataList){
            //查询从数据库中中查数据
            QueryWrapper<Supplier> ew=new QueryWrapper<>();
            ew.eq("SUPPLIER_NAME",supplier.getSupplierName()).or().eq("SUPPLIER_NO",supplier.getSupplierNo());
            List<Supplier> supplierList=this.list(ew);
            //判断是否重复
            if(!CollectionUtils.isEmpty(supplierList)){
                for(Supplier supplier1:supplierList){
                    if (supplier1.getSupplierNo().equals(supplier.getSupplierNo())) {
                        throw new BusinessException("供应商代码"+supplier.getSupplierNo()+"已存在");
                    }
                    if(supplier1.getSupplierName().equals(supplier.getSupplierName())){
                        throw new BusinessException("供应商名称"+supplier.getSupplierName()+"已存在");
                    }
                }
            }
        }
        //拷贝数据
        Collection<Supplier> supplierEntityList=new ArrayList<>();
        for (ReadExcelSupplier supplier:dataList){
            Supplier supplierEntity=new Supplier();
            BeanUtils.copyProperties(supplier,supplierEntity);
            supplierEntityList.add(supplierEntity);
        }
        //执行保存
        QueryWrapper<Supplier> ew=new QueryWrapper<>();
        boolean result =this.saveBatch(supplierEntityList);
        return result;

    }

    @Override
    public Supplier selectBySupplierNo(String supplierNo) {
        QueryWrapper<Supplier> supplierQueryWrapper =new QueryWrapper<>();
        supplierQueryWrapper.eq("SUPPLIER_NO", supplierNo);
        List<Supplier> list = this.list(supplierQueryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("根据供应商代码查询不到信息");
        }
        return list.get(0);
    }

}
