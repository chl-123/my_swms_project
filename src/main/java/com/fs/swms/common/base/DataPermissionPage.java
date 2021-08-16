package com.fs.swms.common.base;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class DataPermissionPage<T> extends Page {

    /**
     * 数据权限中，需要过滤的表字段organizationId的别名
     */
    private String orgIdAlias;

    /**
     * 数据权限中，拥有该权限organizationId的列表
     */
    private List<String> orgIdList;

    /**
     * 数据权限中，需要过滤的表字段userId的别名
     */
    private String userIdAlias;

    /**
     * 数据权限中，拥有该权限userId的值
     */
    private String userId;

    /**
     * 数据权限中，没有本部门数据权限时，是否可以查询本人数据
     */
    private boolean ownQuery;
}