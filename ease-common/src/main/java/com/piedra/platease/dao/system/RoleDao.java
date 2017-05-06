package com.piedra.platease.dao.system;


import com.piedra.platease.dao.BaseDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;

import java.util.List;
import java.util.Set;

/**
 * Dao操作接口定义
 * @author webinglin
 * @since 2017-05-03
 */
public interface RoleDao extends BaseDao<Role> {

    /**
     * 查询角色拥有的所有权限信息
     * @param roleId    角色ID
     * @return  返回角色的权限
     */
    List<Function> queryRolePermissions(String roleId);

    /**
     * 新增角色权限关联
     * @param roleId        角色ID
     * @param newFuncIds    权限ID集合
     */
    void addRoleFuncs(String roleId, Set<String> newFuncIds);

    /**
     * 删除角色权限关联
     * @param roleId        角色ID
     * @param delFuncIds    要删除的权限ID
     */
    void deleteRoleFuncs(String roleId, Set<String> delFuncIds);

    /**
     * 更新角色信息
     * @param role  角色
     */
    void updateRole(Role role);
}
