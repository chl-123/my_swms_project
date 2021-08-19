package com.fs.swms.mainData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.mainData.entity.ProblemType;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-16
 */
public interface ProblemTypeMapper extends BaseMapper<ProblemType> {
    /**
     * 根据问题类型名称查询问题类型列表
     * @param page
     * @param problemType
     * @return Page<ProblemType>
     */
    Page<ProblemType> selectProblemTypeList(Page<ProblemType> page, @Param("problemType") ProblemType problemType);

    /**
     * 查询全部问题类型列表
     * @param page
     * @return Page<ProblemType>
     */
    Page<ProblemType> selectProblemTypeAll(Page<ProblemType> page);


}
