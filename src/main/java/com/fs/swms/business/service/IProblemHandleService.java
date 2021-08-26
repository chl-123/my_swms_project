package com.fs.swms.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.business.dto.UpdateProblemHandle;
import com.fs.swms.business.entity.ProblemHandle;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2021-08-23
 */
public interface IProblemHandleService extends IService<ProblemHandle> {

    Page<ProblemHandle> selectProblemHandleList(Page<ProblemHandle> page, ProblemHandle problemHandle);

    Page<ProblemHandle> selectProblemHandleAll(Page<ProblemHandle> page);

    boolean deleteProblemHandle(String id);

    boolean updateProblemHandle(UpdateProblemHandle problemHandle);

    ProblemHandle selectProblemHandleById(String problemHandleId);


}
