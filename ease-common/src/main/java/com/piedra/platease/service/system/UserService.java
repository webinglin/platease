package com.piedra.platease.service.system;

import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.BaseService;

/**
 * 用户业务实现类
 * @author webinglin
 * @since 2017-04-05
 */
public interface UserService extends BaseService<User, Integer> {

	/**
	 * 获取用户的分页对象
	 * @param pageSize
	 * @param nowPage
	 * @return
	 */
    Page<User> getUserPage(Integer pageSize, Integer nowPage);
}
