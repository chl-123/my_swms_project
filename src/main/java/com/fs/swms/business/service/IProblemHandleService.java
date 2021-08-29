package com.fs.swms.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.business.dto.*;
import com.fs.swms.business.entity.ProblemHandle;
import com.fs.swms.security.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2021-08-23
 */
public interface IProblemHandleService extends IService<ProblemHandle> {

    public Page<ProblemHandleInfo> selectProblemHandleList(Page<ProblemHandleInfo> page, QueryProblemHandle problemHandle,User user);

    boolean updateProblemHandle(UpdateProblemHandle problemHandle,List<MultipartFile> files);

    ProblemHandleInfoEntity selectProblemHandleById(String problemHandleId);


    boolean transfer(CreateProblemHandle problemHandle, User user);

    boolean handle(CreateProblemHandle problemHandle, List<MultipartFile> files);



    boolean cancel(String problemHandleId, String approvalSheetId,User user);

    Page<ProblemHandleInfo> selectProblemHandleListForManagement(Page<ProblemHandleInfo> page, QueryProblemHandle problemHandle,User user);
}
