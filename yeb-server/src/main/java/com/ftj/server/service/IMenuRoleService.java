package com.ftj.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ftj.server.pojo.MenuRole;
import com.ftj.server.pojo.RespBean;

/**
 *
 * @author fengtj
 */
public interface IMenuRoleService extends IService<MenuRole> {

	/**
	 * 更新角色菜单
	 * @param rid
	 * @param mids
	 * @return
	 */
	RespBean updateMenuRole(Integer rid, Integer[] mids);
}
