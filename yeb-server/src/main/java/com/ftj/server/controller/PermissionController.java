package com.ftj.server.controller;

import com.ftj.server.pojo.RespBean;
import com.ftj.server.pojo.Role;
import com.ftj.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限组
 * Created by fengtj on 2022/5/8 14:44
 */
@RestController
@RequestMapping("system/basic/permiss")
public class PermissionController {

    @Autowired
    private IRoleService iRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getRoles() {
        return iRoleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (iRoleService.save(role)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/role/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid) {
        if (iRoleService.removeById(rid)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
