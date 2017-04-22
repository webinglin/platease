package com.piedra.platease.dao.system;


import com.piedra.platease.dao.BaseDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;

import java.util.List;

/**
 * Dao操作接口定义
 * @author webinglin
 * @since 2017-04-05
 */
public interface UserDao extends BaseDao<User> {

    List<Role> queryUserRoles(String userId);

    List<Function> queryUserPermissions(String userId);
}
