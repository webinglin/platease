package com.piedra.platease.service.system;

import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.BaseService;

import java.util.List;

/**
 * 用户业务实现类
 * @author webinglin
 * @since 2017-04-05
 */
public interface UserService extends BaseService<User> {

    /**
     * 根据用户名查询用户信息
     * @param username  用户名
     * @return  返回用户信息
     */
    User getByUsername(String username) throws Exception;

    /**
     * 根据用户ID查询用户的角色
     * @param userId    用户ID
     * @return  返回用户拥有的角色
     */
    List<Role> queryUserRoles(String userId) throws Exception;

    /**
     * 根据用户ID查询用户权限（菜单，资源，按钮等）
     * @param userId    用户ID
     * @return  返回用户的权限
     */
    List<Function> queryUserPermissions(String userId) throws Exception;
}
