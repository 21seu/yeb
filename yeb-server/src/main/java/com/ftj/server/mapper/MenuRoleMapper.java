package com.ftj.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ftj.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fengtj on 2022/5/8 15:10
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
