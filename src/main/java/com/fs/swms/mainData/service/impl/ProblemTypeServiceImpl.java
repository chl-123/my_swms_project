package com.fs.swms.mainData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.excel.ExcelUtil;
import com.fs.swms.mainData.dto.CreateProblemType;
import com.fs.swms.mainData.dto.ReadExcelProblemType;
import com.fs.swms.mainData.dto.UpdateProblemType;
import com.fs.swms.mainData.entity.ProblemType;
import com.fs.swms.mainData.mapper.ProblemTypeMapper;
import com.fs.swms.mainData.service.IProblemTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chl
 * @since 2021-08-16
 */
@Service
public class ProblemTypeServiceImpl extends ServiceImpl<ProblemTypeMapper, ProblemType> implements IProblemTypeService {
    @Autowired
    ProblemTypeMapper problemTypeMapper;

    @Override
    public boolean createProblemType(CreateProblemType problemType) {
        QueryWrapper<ProblemType> ew = new QueryWrapper<>();
        //查找相应的条件
        ew.eq("TYPE_NAME", problemType.getTypeName())/*.or().eq("SUPPLIER_NO",problemType.getProblemTypeNo())*/;
        List<ProblemType> problemTypeList = this.list(ew);
        if (!CollectionUtils.isEmpty(problemTypeList)) {//判断是否重复
            throw new BusinessException("问题名称已存在");
        }
        //拷贝数据
        ProblemType problemTypeEntity = new ProblemType();
        BeanUtils.copyProperties(problemType, problemTypeEntity);
        //执行保存
        boolean result = this.save(problemTypeEntity);
        return result;
    }


    @Override
    public Page<ProblemType> selectProblemTypeList(Page<ProblemType> page, ProblemType problemType) {
        Page<ProblemType> problemTypePage = problemTypeMapper.selectProblemTypeList(page, problemType);
        return problemTypePage;
    }

    @Override
    public Page<ProblemType> selectProblemTypeAll(Page<ProblemType> page) {
        Page<ProblemType> problemTypePage = problemTypeMapper.selectProblemTypeAll(page);
        return problemTypePage;
    }

    @Override
    public boolean deleteProblemType(String id) {
        boolean result = this.removeById(id);
        return result;
    }

    @Override
    public boolean updateProblemType(UpdateProblemType problemType) {
        //查询从数据库中中查数据
        QueryWrapper<ProblemType> ew = new QueryWrapper<>();

        ew.ne("ID", problemType.getTypeName())
                .and(e -> e.eq("TYPE_NAME", problemType.getTypeName()));
        List<ProblemType> problemTypeList = this.list(ew);
        if (!CollectionUtils.isEmpty(problemTypeList)) {//判断是否重复
            throw new BusinessException("问题类型名称已存在");
        }
        //拷贝数据
        ProblemType problemTypeEntity = new ProblemType();
        BeanUtils.copyProperties(problemType, problemTypeEntity);
        //执行保存
        QueryWrapper<ProblemType> wrapper = new QueryWrapper<>();
        wrapper.eq("ID", problemType.getId());
        boolean result = this.update(problemTypeEntity, wrapper);
        return result;
    }


    @Override
    public boolean batchCreatProblemType(MyFile file) throws Exception {
        if (file.getFile()==null) {
            throw new BusinessException("文件不能为空，请选择文件上传");
        }
        Map<String, Integer> map2 = new HashMap<>();
        //读取Excel表格获取数据
        List<ReadExcelProblemType> dataList = ExcelUtil.read(file, ReadExcelProblemType.class);
        for (ReadExcelProblemType problemType : dataList) {

            if (problemType.getTypeName() == null) {
                throw new BusinessException("问题类型名称不能为空");
            }
            if (map2.containsKey(problemType.getTypeName())) {
                map2.clear();
                throw new BusinessException("问题类型名称重复");
            }else {
                map2.put(problemType.getTypeName(), 1);
            }

            if (problemType.getParentId() == null) {
                throw new BusinessException("上一级问题类型不能为空");
            }

        }

        for (ReadExcelProblemType problemType : dataList) {
            //查询从数据库中中查数据
            QueryWrapper<ProblemType> ew = new QueryWrapper<>();
            ew.eq("TYPE_NAME",problemType.getTypeName());
            List<ProblemType> problemTypeList = this.list(ew);
            //判断是否重复
            if (!CollectionUtils.isEmpty(problemTypeList)) {
                throw new BusinessException("问题名称" + problemType.getTypeName() + "已存在");
            }
        }
        //拷贝数据
        Collection<ProblemType> problemTypeEntityList = new ArrayList<>();
        for (ReadExcelProblemType problemType : dataList) {
            ProblemType problemTypeEntity = new ProblemType();
            BeanUtils.copyProperties(problemType, problemTypeEntity);
            problemTypeEntityList.add(problemTypeEntity);
        }
        //执行保存
        QueryWrapper<ProblemType> ew = new QueryWrapper<>();
        boolean result = this.saveBatch(problemTypeEntityList);
        return result;
    }

    @Override
    public ProblemType selectProblemTypeById(String id) {
        ProblemType problemType = problemTypeMapper.selectById(id);
        return problemType;
    }
}
