package com.fs.swms.security.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.security.dto.UpdateDataPermission;
import com.fs.swms.security.entity.DataPermission;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IDataPermissionService extends IService<DataPermission> {

    /**
     * 修改用户数据权限
     * @param updateDataPermission
     * @return
     */
    boolean updateUserDataPermission(UpdateDataPermission updateDataPermission);
}