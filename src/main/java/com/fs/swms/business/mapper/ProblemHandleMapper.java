package com.fs.swms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.dto.ProblemHandleInfo;
import com.fs.swms.business.dto.QueryProblemHandle;
import com.fs.swms.business.entity.ProblemHandle;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-23
 */
public interface ProblemHandleMapper extends BaseMapper<ProblemHandle> {

    Page<ProblemHandleInfo> selectProblemHandleForManagementList(Page<ProblemHandleInfo> page, QueryProblemHandle problemHandle);
}
