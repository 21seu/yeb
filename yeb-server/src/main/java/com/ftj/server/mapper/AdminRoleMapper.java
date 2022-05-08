package com.ftj.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ftj.server.pojo.AdminRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengtj
 * @since 2021-08-28
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
