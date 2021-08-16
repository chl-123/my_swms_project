package com.fs.swms.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.security.dto.UpdateDataPermission;
import com.fs.swms.security.entity.DataPermission;
import com.fs.swms.security.mapper.DataPermissionMapper;
import com.fs.swms.security.service.IDataPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataPermissionServiceImpl extends ServiceImpl<DataPermissionMapper, DataPermission> implements IDataPermissionService {

    @Override
    public boolean updateUserDataPermission(UpdateDataPermission updateDataPermission){

        boolean result = false;
        String userId = updateDataPermission.getUserId();

        List<String> removeDataPermissions = updateDataPermission.getRemoveDataPermissions();
        if (!CollectionUtils.isEmpty(removeDataPermissions) && null != userId)
        {
            QueryWrapper<DataPermission> removeWrapper = new QueryWrapper<>();
            removeWrapper.eq("user_id", userId).in("organization_id", removeDataPermissions);
            result = this.remove(removeWrapper);
        }

        List<String> addDataPermissions = updateDataPermission.getAddDataPermissions();
        if (!CollectionUtils.isEmpty(addDataPermissions) && null != userId)
        {
            List<DataPermission> dataPermissionList = new ArrayList<>();
            for (String dataId: addDataPermissions)
            {
                DataPermission dataPermission = new DataPermission();
                dataPermission.setOrganizationId(dataId);
                dataPermission.setUserId(userId);
                dataPermissionList.add(dataPermission);
            }
            result = this.saveBatch(dataPermissionList);
        }
        return result;
    }

}
