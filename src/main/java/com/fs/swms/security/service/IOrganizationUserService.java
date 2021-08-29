package com.fs.swms.security.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.security.entity.Organization;
import com.fs.swms.security.entity.OrganizationUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 */
public interface IOrganizationUserService extends IService<OrganizationUser> {
    /**
     * 根据用户ID获取组织机构(目前只支持单一组织机构)
     *
     * @param userId
     *            用户主键
     * @return Organization
     *            组织机构
     */
    Organization queryOrganizationByUserId(String userId) ;
}

