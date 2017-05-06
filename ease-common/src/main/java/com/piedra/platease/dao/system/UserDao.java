package com.piedra.platease.dao.system;


import com.piedra.platease.dao.BaseDao;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;

import java.util.List;
import java.util.Set;

/**
 * Dao操作接口定义
 * @author webinglin
 * @since 2017-04-05
 */
public interface UserDao extends BaseDao<User> {

    /**
     * 根据用户ID查询当前登录用户的所有角色
     * @param userId   用户ID
     * @return  返回用户角色信息
     */
    List<Role> queryUserRoles(String userId);

    /**
     * 根据用户ID查询用户的权限资源
     * @param userId    用户ID
     * @return  返回用户拥有的资源权限信息
     */
    List<Function> queryUserPermissions(String userId);

    /**
     *  更新用户信息
     * @param user  待更新的用户信息
     */
    void updateUser(User user) throws Exception ;

    /**
     * 为某个用户添加角色关联
     * @param userId        用户ID
     * @param newRoleIds    角色ID
     */
    void addUserRoles(String userId, Set<String> newRoleIds);

    /**
     * 删除某个用户的角色ID
     * @param userId       用户ID
     * @param delRoleIds   角色ID
     */
    void deleteUserRoles(String userId, Set<String> delRoleIds);
}
