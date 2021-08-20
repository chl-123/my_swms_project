package com.fs.swms.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.common.annotation.log.AroundLog;
import com.fs.swms.common.base.PageResult;
import com.fs.swms.common.base.Result;
import com.fs.swms.security.dto.*;
import com.fs.swms.security.entity.Role;
import com.fs.swms.security.entity.RoleResource;
import com.fs.swms.security.service.IRoleResourceService;
import com.fs.swms.security.service.IRoleService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: RoleController
 * @Description: Role前端控制器
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午4:06:17
 */
@RestController
@RequestMapping("/role")
@Api(value = "RoleController|角色相关的前端控制器")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRoleResourceService roleResourceService;

    /**
     * 查询角色列表
     */
    @GetMapping("/list")
    /*@RequiresRoles("SYSADMIN")*/
    @ApiOperation(value = "查询角色列表")
    public PageResult<Role> list(Role role, Page<Role> page) {
        System.out.println(role.getParentId());
        System.out.println(page.getCurrent());
        Page<Role> pageRole = roleService.selectRoleList(page, role);
        PageResult<Role> pageResult = new PageResult<Role>(pageRole.getTotal(), pageRole.getRecords());
        return pageResult;
    }

    /**
     * 添加角色
     */
    @PostMapping("/create")
//    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加角色")
    @AroundLog(name = "添加角色")
    public Result<?> create(@RequestBody CreateRole role) {
        boolean result = roleService.createRole(role);
        if (result) {
            return new Result<>().success("添加成功");
        } else {
            return new Result<>().error("添加失败，请重试");
        }
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新角色")
    @AroundLog(name = "更新角色")
    public Result<?> update(@RequestBody UpdateRole role) {
        boolean result = roleService.updateRole(role);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }

    /**
     * 删除角色
     */
    @RequiresRoles("SYSADMIN")
    @PostMapping("/delete/{roleId}")
    @ApiOperation(value = "删除角色")
    @AroundLog(name = "删除角色")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("roleId") String roleId) {
        if (null == roleId) {
            return new Result<>().error("ID不能为空");
        }
        boolean result = roleService.deleteRole(roleId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 批量删除角色
     */
    @RequiresRoles("SYSADMIN")
    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除角色")
    @AroundLog(name = "批量删除角色")
    @ApiImplicitParam(name = "roleIds", value = "角色ID列表", required = true, dataType = "List")
    public Result<?> batchDelete(@RequestBody List<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return new Result<>().error("角色ID列表不能为空");
        }
        boolean result = roleService.batchDeleteRole(roleIds);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 修改角色状态
     */
    @PostMapping("/status/{roleId}/{roleStatus}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "修改角色状态")
    @AroundLog(name = "修改角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "roleStatus", value = "角色状态", required = true, dataType = "String", paramType = "path") })
    public Result<?> updateStatus(@PathVariable("roleId") String roleId,
                                  @PathVariable("roleStatus") String roleStatus) {
        if (null == roleId || !StringUtils.hasText(roleStatus)) {
            return new Result<>().error("ID和状态不能为空");
        }
        UpdateRole role = new UpdateRole();
        role.setId(roleId);
        role.setRoleStatus(roleStatus);
        boolean result = roleService.updateRole(role);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }

    /**
     * 获取角色资源
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/resource/{roleId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "获取角色的权限资源")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色ID", required = true, dataType = "String")
    public Result<List<RoleResource>> queryRoleResource(@PathVariable("roleId") String roleId) {
        QueryWrapper<RoleResource> ew = new QueryWrapper<>();
        ew.eq("role_id", roleId);
        List<RoleResource> list = roleResourceService.list(ew);
        return new Result<List<RoleResource>>().success().put(list);
    }

    /**
     * 修改角色资源
     *
     * @param updateRoleResource
     * @return
     */
    @PostMapping(value = "/resource/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "修改角色的权限资源")
    @AroundLog(name = "修改角色的权限资源")
    public Result<?> updateRoleResource(@RequestBody UpdateRoleResource updateRoleResource) {
        boolean result = roleResourceService.updateList(updateRoleResource);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }

    /**
     * 查询所有角色列表
     *
     * @return
     */
    @GetMapping(value = "/all")
    @RequiresAuthentication
    @ApiOperation(value = "查询所有角色列表")
    public Result<List<Role>> queryAll() {
        QueryWrapper<Role> ew = new QueryWrapper<>();
        List<Role> result = roleService.list(ew);
        return new Result<List<Role>>().success().put(result);
    }

    @PostMapping(value = "/name/check")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "校验角色名称是否存在", notes = "校验角色名称是否存在")
    public Result<Boolean> checkRoleName(CreateRole role) {
        String roleName = role.getRoleName();
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role_name", roleName);
        if(null != role.getId()) {
            roleQueryWrapper.ne("id", role.getId());
        }
        int count = roleService.count(roleQueryWrapper);
        if (count == 0){
            return new Result<Boolean>().success().put(true);
        } else{
            return new Result<Boolean>().success().put(false);
        }
    }

    @PostMapping(value = "/key/check")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "校验角色标识是否存在", notes = "校验角色标识是否存在")
    public Result<Boolean> checkRoleKey(CreateRole role) {
        String roleKey = role.getRoleKey();
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role_key", roleKey);
        if(null != role.getId()) {
            roleQueryWrapper.ne("id", role.getId());
        }
        int count = roleService.count(roleQueryWrapper);
        if (count == 0){
            return new Result<Boolean>().success().put(true);
        } else{
            return new Result<Boolean>().success().put(false);
        }
    }
}
