package com.ftj.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ftj.server.pojo.Menu;
import com.ftj.server.pojo.MenuRole;
import com.ftj.server.pojo.RespBean;
import com.ftj.server.pojo.Role;
import com.ftj.server.service.IMenuRoleService;
import com.ftj.server.service.IMenuService;
import com.ftj.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组
 * Created by fengtj on 2022/5/8 14:44
 */
@RestController
@RequestMapping("system/basic/permiss")
public class PermissionController {

    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IMenuService iMenuService;
    @Autowired
    private IMenuRoleService iMenuRoleService;

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

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getMenus() {
        return iMenuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询所有菜单")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid) {
        return iMenuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid))
                .stream()
                .map(MenuRole::getId)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        return iMenuRoleService.updateMenuRole(rid, mids);
    }
}
