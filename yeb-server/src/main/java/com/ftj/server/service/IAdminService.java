package com.ftj.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ftj.server.pojo.Admin;
import com.ftj.server.pojo.AdminLoginParam;
import com.ftj.server.pojo.RespBean;
import com.ftj.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengtj
 * @since 2021-08-28
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     * @param adminLoginParam
     * @param request
     * @return
     */
    RespBean login(AdminLoginParam adminLoginParam, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 根据用户id查询角色列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
