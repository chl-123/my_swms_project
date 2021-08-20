package com.fs.swms.security.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.security.dto.QueryOrganization;
import com.fs.swms.security.entity.Organization;

import java.util.List;

/**
 *
 * @author jeebase
 */
public interface IOrganizationService extends IService<Organization> {

    /**
     * 查询机构列表
     * @param parentId
     * @return
     */
    List<Organization> queryOrganizationByPanentId(String parentId);

    /**
     * 查询机构列表，不组装父子节点
     * @param parentId
     * @return
     */
    List<Organization> queryOrgList(String parentId);

    /**
     * 创建组织
     * @param organization
     * @return
     */
    boolean createOrganization(Organization organization);

    /**
     * 更新组织
     * @param organization
     * @return
     */
    boolean updateOrganization(Organization organization);

    /**
     * 删除组织
     * @param organizationId
     * @return
     */
    boolean deleteOrganization(String organizationId);

    /**
     * 批量添加部门信息
     * @param file
     * @return boolean
     */
    boolean batchCreateOrganization(MyFile file) throws Exception;

    /**
     * 根据条件查询部门信息
     * @param organization
     * @param page
     * @return Page<Organization>
     */
    Page<Organization> queryOrgPage(QueryOrganization organization, Page<Organization> page);
    /**
     * 查询全部部门信息
     * @param page
     * @return Page<Organization>
     */
    Page<Organization> queryAllOrgPage(Page<Organization> page);
}
