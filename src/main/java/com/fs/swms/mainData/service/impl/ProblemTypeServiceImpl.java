package com.fs.swms.mainData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.common.excel.ExcelUtil;
import com.fs.swms.mainData.dto.*;
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
        boolean result=false;
        QueryWrapper<ProblemType> ew = new QueryWrapper<>();
        //查找相应的条件
        ew.eq("TYPE_NAME", problemType.getTypeName())/*.or().eq("SUPPLIER_NO",problemType.getProblemTypeNo())*/;
        List<ProblemType> problemTypeList = this.list(ew);
        if (!CollectionUtils.isEmpty(problemTypeList)) {//判断是否重复
            throw new BusinessException("问题名称已存在");
        }
        if (problemType.getId()!=null) {
            QueryWrapper<ProblemType> queryWrapper = new QueryWrapper<>();
            //查找相应的条件
            queryWrapper.eq("ID", problemType.getId())/*.or().eq("SUPPLIER_NO",problemType.getProblemTypeNo())*/;
            List<ProblemType> list = this.list(ew);
            if (CollectionUtils.isEmpty(list)) {//判断是否重复
                throw new BusinessException("该问题父级不存在");
            }
            ProblemType problemTypeEntity = new ProblemType();
            problemTypeEntity.setTypeName(problemType.getTypeName());
            problemTypeEntity.setParentId(problemType.getId());
            //执行保存
            result = this.save(problemTypeEntity);
        }else {
            ProblemType problemTypeEntity = new ProblemType();
            BeanUtils.copyProperties(problemType, problemTypeEntity);
            //执行保存
            result = this.save(problemTypeEntity);
        }
        //拷贝数据

        return result;
    }


    @Override
    public Page<ProblemTypeInfo> selectProblemTypeList(Page<ProblemTypeInfo> page, ProblemType problemType) {
        Page<ProblemTypeInfo> problemTypePage = problemTypeMapper.selectProblemTypeList(page, problemType);
        List<ProblemTypeInfo> problemTypeInfoList = problemTypePage.getRecords();
        for (ProblemTypeInfo problemTypeInfo:problemTypeInfoList) {
            ProblemType byId = this.getById(problemTypeInfo.getParentId());
            if (byId != null) {
                problemTypeInfo.setParentTypeName(byId.getTypeName());
            }
        }
        return problemTypePage;
    }

    @Override
    public Page<ProblemTypeInfo> selectProblemTypeAll(Page<ProblemTypeInfo> page) {
        ProblemType problemType=new ProblemType();
        Page<ProblemTypeInfo> problemTypePage = problemTypeMapper.selectProblemTypeList(page, problemType);
        List<ProblemTypeInfo> problemTypeInfoList = problemTypePage.getRecords();
        for (ProblemTypeInfo problemTypeInfo:problemTypeInfoList) {
            ProblemType byId = this.getById(problemTypeInfo.getParentId());
            if (byId != null) {
                problemTypeInfo.setParentTypeName(byId.getTypeName());
            }
        }
        return problemTypePage;
    }

    @Override
    public boolean deleteProblemType(String id) {
        QueryWrapper<ProblemType> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("PARENT_ID", id);
        List<ProblemType> list = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException("当前问题类型包含子问题类型，如果删除，则子问题类型会一并删除，是否继续删除?");
        }
        boolean result = this.removeById(id);
        return result;
    }
    public boolean deleteProblemTypeAll(String id) {
        boolean result=false;
        QueryWrapper<ProblemType> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("PARENT_ID", id);
        List<ProblemType> list = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            for(ProblemType problemType:list){
                result = this.removeById(problemType.getId());
            }
            result = this.removeById(id);
        }

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
        String sheetName="问题类型基础信息_质管科";
        List<ReadExcelProblemType> dataList = ExcelUtil.read(file, ReadExcelProblemType.class,sheetName);
        if (dataList.size()==0) {

            throw new BusinessException("基础数据模板中【"+sheetName+"】这个Excel表没有数据");
        }
        for (ReadExcelProblemType problemType : dataList) {

            if (problemType.getTypeName() == null) {
                throw new BusinessException("问题类型名称不能为空");
            }
            if (map2.containsKey(problemType.getTypeName()+problemType.getParentTypeName())) {

                throw new BusinessException("文档中问题类型【"+problemType.getTypeName()+"】名称重复");
            }else {
                map2.put(problemType.getTypeName(), 1);

            }

            if (problemType.getParentTypeName() == null) {
                throw new BusinessException("文档中的上级问题类型名称不能为空");
            }

        }

        for (ReadExcelProblemType problemType : dataList) {
            //查询从数据库中中查数据
            QueryWrapper<ProblemType> ew = new QueryWrapper<>();
            ew.eq("TYPE_NAME",problemType.getTypeName());
            List<ProblemType> problemTypeList = this.list(ew);
            //判断是否重复
            if (!CollectionUtils.isEmpty(problemTypeList)) {
                throw new BusinessException("问题名称【" + problemType.getTypeName() + "】已存在");
            }
        }
        boolean result=false;
        //拷贝数据
        Collection<ProblemType> problemTypeEntityList = new ArrayList<>();
       /* List<String> parentTypeNameList=new ArrayList<>();*/
        for (ReadExcelProblemType problemType : dataList) {

            ProblemType problemTypeEntity = new ProblemType();
            problemTypeEntity.setTypeName(problemType.getTypeName());
            problemTypeEntityList.add(problemTypeEntity);
           /* if (!parentTypeNameList.contains(problemType.getParentTypeName())&&(problemType.getParentTypeName()!="0")) {
                parentTypeNameList.add(problemType.getParentTypeName());
            }*/
        }
        //执行保存
        QueryWrapper<ProblemType> ew = new QueryWrapper<>();
        result = this.saveBatch(problemTypeEntityList);

        for (ReadExcelProblemType problemType : dataList) {
            if (!problemType .getParentTypeName().equals("0")) {
                UpdateWrapper<ProblemType> typeUpdateWrapper=new UpdateWrapper<>();
                QueryWrapper<ProblemType> queryWrapper1=new QueryWrapper<>();
                QueryWrapper<ProblemType> queryWrapper2=new QueryWrapper<>();
                queryWrapper1.eq("TYPE_NAME",problemType.getTypeName());
                queryWrapper2.eq("TYPE_NAME",problemType.getParentTypeName());
                List<ProblemType> typeList1 = this.list(queryWrapper1);
                List<ProblemType> typeList2 = this.list(queryWrapper2);
                if (!CollectionUtils.isEmpty(typeList1)&&!CollectionUtils.isEmpty(typeList2)) {

                    typeUpdateWrapper.set("PARENT_ID",typeList2.get(0).getId());
                    typeUpdateWrapper.set("UPDATE_TIME","");
                    typeUpdateWrapper.set("OPERATOR","");
                    typeUpdateWrapper.eq("ID",typeList1.get(0).getId());
                    result=this.update(typeUpdateWrapper);
                }
            }

        }

        return result;
    }

    @Override
    public ProblemTypeInfo selectProblemTypeById(String id) {
        ProblemType problemType = problemTypeMapper.selectById(id);
        ProblemTypeInfo problemTypeInfo=new ProblemTypeInfo();

        if (problemType == null) {
            throw new BusinessException("根据ID查询不到数据");
        }
        BeanUtils.copyProperties(problemType,problemTypeInfo);
        ProblemType parentProblemType = problemTypeMapper.selectById(problemType.getParentId());
        if (parentProblemType!=null) {
            problemTypeInfo.setParentTypeName(parentProblemType.getTypeName());
        }
        return problemTypeInfo;
    }

    @Override
    public List<ProblemTypeTree> queryProblemTypeTreeByParentId(String parentId) {
        if (null == parentId) {
            parentId = "0";
        }
        List<ProblemTypeTree> orgs = new ArrayList<ProblemTypeTree>();
        try {
            List<ProblemTypeTree> orgList = problemTypeMapper.queryProblemTypeTree(parentId);
            Map<String, ProblemTypeTree> problemTypeMap = new HashMap<String, ProblemTypeTree>();
            // 组装子父级目录
            for (ProblemTypeTree problemType : orgList) {
                problemTypeMap.put(problemType.getId(), problemType);
            }
            for (ProblemTypeTree problemType : orgList) {
                String treePId = problemType.getParentId();
                ProblemTypeTree pTreev = problemTypeMap.get(treePId);
                if (null != pTreev && !problemType.equals(pTreev)) {
                    List<ProblemTypeTree> nodes = pTreev.getChildren();
                    if (null == nodes) {
                        nodes = new ArrayList<ProblemTypeTree>();
                        pTreev.setChildren(nodes);
                    }
                    nodes.add(problemType);
                } else {
                    orgs.add(problemType);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("查询组织树失败");
        }
        return orgs;

    }
   /* public ProblemType insertProblemType(ProblemType problemType) {
        baseMapper.insert(problemType);
        return problemType;
    }*/
}
