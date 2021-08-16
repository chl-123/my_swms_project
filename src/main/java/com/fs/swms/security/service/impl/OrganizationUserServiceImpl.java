package com.fs.swms.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.swms.security.entity.OrganizationUser;
import com.fs.swms.security.mapper.OrganizationUserMapper;
import com.fs.swms.security.service.IOrganizationUserService;
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
}
