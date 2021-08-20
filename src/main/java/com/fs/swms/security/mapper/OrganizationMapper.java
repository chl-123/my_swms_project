package com.fs.swms.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.security.entity.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织表 Mapper 接口
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * 查询组织机构树
     * @param parentId
     * @return
     */
    List<Organization> queryOrganizationTree(String parentId);

    /**
     * 根据条件查询部门信息
     * @param page
     * @param organization
     * @return Page<Organization>
     */
    Page<Organization> queryOrgBySelective(Page<Organization> page, @Param("organization") Organization organization);
    /**
     * 查询全部部门信息
     * @param page
     * @return Page<Organization>
     */
    Page<Organization> queryAllOrg(Page<Organization> page);

}