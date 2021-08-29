package com.fs.swms.mainData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateProblemType;
import com.fs.swms.mainData.dto.ProblemTypeInfo;
import com.fs.swms.mainData.dto.ProblemTypeTree;
import com.fs.swms.mainData.dto.UpdateProblemType;
import com.fs.swms.mainData.entity.ProblemType;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2021-08-16
 */
public interface IProblemTypeService extends IService<ProblemType> {
    /**
     * 创建问题类型信息
     * @param problemType
     * @return boolean
     */
    boolean createProblemType(CreateProblemType problemType);
    /**
     * 批量创建问题类型信息
     * @param file
     * @return boolean
     */
    boolean batchCreatProblemType(MyFile file) throws Exception;
    /**
     * 根据问题类型名称查询分页
     * @param problemType
     * @param page
     * @return Page<ProblemType>
     */
    Page<ProblemTypeInfo> selectProblemTypeList(Page<ProblemTypeInfo> page, ProblemType problemType);
    /**
     * 查询所有问题类型
     * @param page
     * @return Page<ProblemType>
     */
    Page<ProblemTypeInfo> selectProblemTypeAll(Page<ProblemTypeInfo> page);
    /**
     * 根据问题类型ID删除问题类型信息
     * @param id
     * @return boolean
     */
    boolean deleteProblemType(String id);
    /**
     * 更新问题类型信息
     * @param problemType
     * @return boolean
     */
    boolean updateProblemType(UpdateProblemType problemType);

    /**
     * 根据问题ID查询
     * @param id
     * @return ProblemType
     */
    ProblemTypeInfo selectProblemTypeById(String id);


    boolean deleteProblemTypeAll(String id);

    List<ProblemTypeTree> queryProblemTypeTreeByParentId(String parentId);

}
