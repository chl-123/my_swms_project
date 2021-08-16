package com.fs.swms.security.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.common.base.BusinessException;
import com.fs.swms.security.entity.Organization;
import com.fs.swms.security.mapper.OrganizationMapper;
import com.fs.swms.security.service.IOrganizationService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jeebase
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization>
        implements IOrganizationService {

    /**
     * 异常日志记录对象
     */
    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    OrganizationMapper organizationMapper;

    /**
     * queryOrgList
     *
     * @Title: queryOrgList
     * @Description: 查询所有的组织结构树
     * @param parentId
     * @return List<Organization>
     */
    @Override
    public List<Organization> queryOrgList(String parentId) {
        List<Organization> orgList;
        try {
            if (null == parentId) {
                parentId = "0";
            }
            orgList = organizationMapper.queryOrganizationTree(parentId);
        } catch (Exception e) {
            logger.error("查询组织树失败:", e);
            throw new BusinessException("查询组织树失败");
        }
        return orgList;
    }

    @Override
    public List<Organization> queryOrganizationByPanentId(String parentId) {
        if (null == parentId) {
            parentId = "0";
        }
        List<Organization> orgs = new ArrayList<Organization>();
        try {
            List<Organization> orgList = organizationMapper.queryOrganizationTree(parentId);
            Map<String, Organization> organizationMap = new HashMap<String, Organization>();
            // 组装子父级目录
            for (Organization organization : orgList) {
                organizationMap.put(organization.getId(), organization);
            }
            for (Organization organization : orgList) {
                String treePId = organization.getParentId();
                Organization pTreev = organizationMap.get(treePId);
                if (null != pTreev && !organization.equals(pTreev)) {
                    List<Organization> nodes = pTreev.getChildren();
                    if (null == nodes) {
                        nodes = new ArrayList<Organization>();
                        pTreev.setChildren(nodes);
                    }
                    nodes.add(organization);
                } else {
                    orgs.add(organization);
                }
            }
        } catch (Exception e) {
            logger.error("查询组织树失败:", e);
            throw new BusinessException("查询组织树失败");
        }
        return orgs;
    }

    @Override
    public boolean createOrganization(Organization organization) {
        QueryWrapper<Organization> ew = new QueryWrapper<>();
        ew.eq("organization_name", organization.getOrganizationName()).or().eq("organization_key", organization.getOrganizationKey());
        List<Organization> organizationList = this.list(ew);
        if (!CollectionUtils.isEmpty(organizationList)) {
            throw new BusinessException("组织名称或组织标识已经存在");
        }
        boolean result = this.save(organization);
        return result;
    }

    @Override
    public boolean updateOrganization(Organization organization) {
        QueryWrapper<Organization> ew = new QueryWrapper<>();
        ew.ne("id", organization.getId()).and(e -> e.eq("organization_name", organization.getOrganizationName()).or().eq("organization_key", organization.getOrganizationKey()));
        List<Organization> organizationList = this.list(ew);
        if (!CollectionUtils.isEmpty(organizationList)) {
            throw new BusinessException("组织名称或组织标识已经存在");
        }
        boolean result = this.updateById(organization);
        return result;
    }

    @Override
    public boolean deleteOrganization(String organizationId) {
        boolean result = false;
        if (null == organizationId)
        {
            throw new BusinessException("请选择要删除的组织");
        }
        List<Organization> orgList = organizationMapper.queryOrganizationTree(organizationId);
        List<String> orgIds = new ArrayList<>();

        for (Organization org: orgList)
        {
            orgIds.add(org.getId());
        }
        if (!CollectionUtils.isEmpty(orgIds))
        {
            result = this.removeByIds(orgIds);
        }
        else
        {
            //删除叶子节点
            result = this.removeById(organizationId);
        }
        return result;
    }
}
