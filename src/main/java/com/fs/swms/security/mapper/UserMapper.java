package com.fs.swms.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.security.dto.QueryUser;
import com.fs.swms.security.dto.UserInfo;
import com.fs.swms.security.entity.User;
import org.apache.ibatis.annotations.Param;

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
}
