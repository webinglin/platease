package com.piedra.platease.service.system;

import com.piedra.platease.model.system.Role;
import com.piedra.platease.service.BaseService;

import java.util.Set;

/**
 * 角色的接口定义
 * @author webinglin
 * @since 2017-05-03
 */
public interface RoleService  extends BaseService<Role> {


    /**
     * 更新某个角色拥有的资源权限列表
     * @param roleId        角色ID
     * @param funcIds    权限ID集合
     * @throws Exception    异常往上层抛出
     */
    void updateRoleFunctions(String roleId, Set<String> funcIds) throws Exception;




}
