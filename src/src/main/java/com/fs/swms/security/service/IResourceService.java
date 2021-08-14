package com.fs.swms.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.security.entity.Resource;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface IResourceService extends IService<Resource> {

    /**
     * 查询用户资源角色
     * @param userId
     * @return List<Resource>
     */
    List<Resource> queryResourceByUserId(String userId);

    @Cacheable(value = "resources", key = "'all_user_id_'.concat(#userId)")
    List<String> queryResourceListByUserId(String userId);

    /**
     * 查询资源权限列表
     * @param parentId
     * @return List<Resource>
     */
    List<Resource> queryResourceByParentId(String parentId);

    /**
     * 查询资源权限列表
     * @param wrapper
     * @return List<Resource>
     */
    List<Resource> selectResourceList(QueryWrapper<Resource> wrapper);

    /**
     * 创建资源权限
     * @param resource
     * @return boolean
     */
    boolean createResource(Resource resource);

    /**
     * 更新资源权限
     * @param resource
     * @return boolean
     */
    boolean updateResource(Resource resource);

    /**
     * 删除资源权限
     * @param resourceId
     * @return boolean
     */
    boolean deleteResource(String resourceId);
}
