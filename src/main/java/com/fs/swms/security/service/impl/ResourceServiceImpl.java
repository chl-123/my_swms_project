package com.fs.swms.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.security.entity.Resource;
import com.fs.swms.security.mapper.ResourceMapper;
import com.fs.swms.security.service.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jeebase
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    /**
     * 异常日志记录对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @CacheEvict(value = "resources", allEntries = true)
    public boolean createResource(Resource resource) {
        String resourceKey = resource.getResourceKey();
        QueryWrapper<Resource> resourceQueryWrapper = new QueryWrapper<>();
        resourceQueryWrapper.eq("resource_key", resourceKey);
        int count = this.count(resourceQueryWrapper);
        if (count > 0) {
            throw new BusinessException("资源标识已存在");
        }

        boolean result = this.save(resource);
        return result;
    }

    @Override
    @CacheEvict(value = "resources", allEntries = true)
    public boolean updateResource(Resource resource) {
        String resourceKey = resource.getResourceKey();
        QueryWrapper<Resource> resourceQueryWrapper = new QueryWrapper<>();
        resourceQueryWrapper.eq("resource_key", resourceKey).ne("id", resource.getId());
        int count = this.count(resourceQueryWrapper);
        if (count > 0) {
            throw new BusinessException("资源标识已存在");
        }
        boolean result = this.updateById(resource);
        return result;
    }

    @Override
    @CacheEvict(value = "resources", allEntries = true)
    public boolean deleteResource(String resourceId) {
        QueryWrapper<Resource> wpd = new QueryWrapper<Resource>();
        wpd.and(e -> e.eq("id", resourceId).or().eq("parent_id", resourceId));
        boolean result = this.remove(wpd);
        return result;
    }

    @Override
    @Cacheable(value = "resources", key = "resources_all")
    public List<Resource> selectResourceList(QueryWrapper<Resource> wrapper) {
        List<Resource> list = this.list(wrapper);
        return list;
    }

    @Override
    @Cacheable(value = "resources", key = "'user_id_'.concat(#userId)")
    public List<Resource> queryResourceByUserId(String userId) {
        List<Resource> resourceList = resourceMapper.queryResourceByUserId(userId);
        Map<String, Resource> resourceMap = new HashMap<>();
        List<Resource> menus = this.assembleResourceTree(resourceList,resourceMap);
        return menus;
    }

    @Override
    @Cacheable(value = "resources", key = "'all_user_id_'.concat(#userId)")
    public List<String> queryResourceListByUserId(String userId) {
        List<Resource> resourceList = resourceMapper.queryResourceByUserId(userId);
        List<String> menus = new ArrayList<>();
        for (Resource resource : resourceList) {
            menus.add(resource.getResourceKey());
        }
        return menus;
    }

    @Override
    @Cacheable(value = "resources", key = "'parent_id_'.concat(#parentId)")
    public List<Resource> queryResourceByParentId(String parentId) {
        List<Resource> resourceList = resourceMapper.queryResourceTreeProc(parentId);
        Map<String, Resource> resourceMap = new HashMap<>();
        List<Resource> menus = this.assembleResourceTree(resourceList,resourceMap);
        return menus;
    }

    /**
     * 组装子父级目录
     * @param resourceList
     * @param resourceMap
     * @return
     */
    private List<Resource> assembleResourceTree(List<Resource> resourceList, Map<String, Resource> resourceMap)
    {
        List<Resource> menus = new ArrayList<>();
        for (Resource resource : resourceList) {
            resourceMap.put(resource.getId(), resource);
        }
        for (Resource resource : resourceList) {
            String treePId = resource.getParentId();
            Resource resourceTree = resourceMap.get(treePId);
            if (null != resourceTree && !resource.equals(resourceTree)) {
                List<Resource> nodes = resourceTree.getChildren();
                if (null == nodes) {
                    nodes = new ArrayList<>();
                    resourceTree.setChildren(nodes);
                }
                nodes.add(resource);
            } else {
                menus.add(resource);
            }
        }
        return menus;
    }
}
