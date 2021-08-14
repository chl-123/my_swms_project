package com.fs.swms.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fs.swms.security.entity.Organization;

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
}