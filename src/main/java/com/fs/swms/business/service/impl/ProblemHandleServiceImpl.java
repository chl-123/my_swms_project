package com.fs.swms.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.business.dto.UpdateProblemHandle;
import com.fs.swms.business.entity.ProblemHandle;
import com.fs.swms.business.mapper.ProblemHandleMapper;
import com.fs.swms.business.service.IProblemHandleService;
import com.fs.swms.common.base.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2021-08-23
 */
@Service
public class ProblemHandleServiceImpl extends ServiceImpl<ProblemHandleMapper, ProblemHandle> implements IProblemHandleService {
    @Autowired
    ProblemHandleMapper problemHandleMapper;


    @Override
    public Page<ProblemHandle> selectProblemHandleList(Page<ProblemHandle> page, ProblemHandle problemHandle) {
        Page<ProblemHandle> problemHandlePage = problemHandleMapper.selectProblemHandleList(page, problemHandle);
        return problemHandlePage;
    }

    @Override
    public Page<ProblemHandle> selectProblemHandleAll(Page<ProblemHandle> page) {
        Page<ProblemHandle> problemHandlePage = problemHandleMapper.selectProblemHandleAll(page);
        return problemHandlePage;
    }


    @Override
    public boolean deleteProblemHandle(String id) {
        boolean result = this.removeById(id);
        return result;
    }

    @Override
    public boolean updateProblemHandle(UpdateProblemHandle problemHandle) {
        //查询从数据库中中查数据
        QueryWrapper<ProblemHandle> ew = new QueryWrapper<>();

        ew.ne("ID", problemHandle.getServiceRedId())
                .and(e -> e.eq("SERVICE_RED_ID", problemHandle.getServiceRedId()));

        ew.ne("ID", problemHandle.getDeptNo())
                .and(e -> e.eq("DEPT_NO", problemHandle.getDeptNo()));

        ew.ne("ID", problemHandle.getExecutor())
                .and(e -> e.eq("EXECUTOR", problemHandle.getExecutor()));

        ew.ne("ID", problemHandle.getProblemId())
                .and(e -> e.eq("PROBLEM_ID", problemHandle.getProblemId()));

        ew.ne("ID", problemHandle.getProcessScheme())
                .and(e -> e.eq("PROCESS_SCHEME", problemHandle.getProcessScheme()));

        List<ProblemHandle> problemHandleList = this.list(ew);
        if (!CollectionUtils.isEmpty(problemHandleList)) {//判断是否重复
            throw new BusinessException("齿轮箱ID已存在");
        }
        //拷贝数据
        ProblemHandle problemHandleEntity = new ProblemHandle();
        BeanUtils.copyProperties(problemHandle, problemHandleEntity);
        //执行保存
        QueryWrapper<ProblemHandle> wrapper = new QueryWrapper<>();
        wrapper.eq("ID", problemHandle.getId());
        boolean result = this.update(problemHandleEntity, wrapper);
        return result;
    }


    @Override
    public ProblemHandle selectProblemHandleById(String id) {
        ProblemHandle problemHandle = problemHandleMapper.selectById(id);
        return problemHandle;
    }
}
