package com.piedra.platease.service.system;

import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.model.Page;
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


    /* ************************************************************************
     *  用户授权相关
     ************************************************************************* */

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


    /* ************************************************************************
     *  用户管理相关
     ************************************************************************* */

    /**
     * 根据分页参数以及查询条件进行分页查询
     * @param page      分页参数
     * @param userDTO   过滤参数
     * @return  返回用户结果信息
     */
    List<User> queryByPage(Page<User> page, UserDTO userDTO) throws Exception;

    /**
     * 更新用户信息
     * @param userDto   用户信息
     * @throws Exception    抛出异常
     */
    void updateUser(UserDTO userDto) throws Exception;


}
