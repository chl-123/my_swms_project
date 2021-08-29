package com.fs.swms.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.security.entity.Organization;
import com.fs.swms.security.entity.OrganizationUser;
import com.fs.swms.security.mapper.OrganizationUserMapper;
import com.fs.swms.security.service.IOrganizationService;
import com.fs.swms.security.service.IOrganizationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 */
@Service
public class OrganizationUserServiceImpl extends ServiceImpl<OrganizationUserMapper, OrganizationUser>
        implements IOrganizationUserService {
    @Autowired
    private IOrganizationService organizationService;

    @Override
    @Cacheable(value = "organization", key = "'role_id_'.concat(#userId)")
    public Organization queryOrganizationByUserId(String userId) {

        QueryWrapper<OrganizationUser> ew = new QueryWrapper<>();
        ew.eq("user_id", userId);
        OrganizationUser organizationUser = this.getOne(ew);

        if (null != organizationUser) {
            String orgId = organizationUser.getOrganizationId();

            QueryWrapper<Organization> ewOrganization = new QueryWrapper<>();
            ewOrganization.in("id", orgId);
            Organization organization = organizationService.getOne(ewOrganization);

            return organization;
        } else {
            return null;
        }
    }
}
