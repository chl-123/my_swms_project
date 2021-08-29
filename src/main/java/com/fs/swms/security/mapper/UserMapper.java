package com.fs.swms.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.security.dto.QueryUser;
import com.fs.swms.security.dto.UserInfo;
import com.fs.swms.security.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户列表
     * @param page
     * @param user
     * @return
     */
    Page<UserInfo> selectUserList(Page<UserInfo> page, @Param("user") QueryUser user);
    /**
     * 根据组织机构Id查询用户列表
     * @param organizationId
     * @return
     */
    List<UserInfo> selectUserListByOrganizationId(@Param("organizationId")String organizationId);
}
