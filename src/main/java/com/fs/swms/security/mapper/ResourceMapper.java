package com.fs.swms.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fs.swms.security.entity.Resource;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查询用户权限资源
     * @param userId
     * @return List<Resource>
     */
    List<Resource> queryResourceByUserId(String userId);

    /**
     * queryResourceTreeProc
     *
     * @Title: queryResourceTreeProc
     * @Description: 查询登陆用户的许可权限
     * @param parentId
     * @return List<Resource>
     */
    List<Resource> queryResourceTreeProc(String parentId);
}
