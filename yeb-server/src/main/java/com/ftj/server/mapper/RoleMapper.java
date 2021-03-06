package com.ftj.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ftj.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengtj
 * @since 2021-08-28
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
