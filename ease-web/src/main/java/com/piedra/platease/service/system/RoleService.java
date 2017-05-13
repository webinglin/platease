package com.piedra.platease.service.system;

import com.piedra.platease.dto.RoleDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.service.BaseService;

import java.util.List;
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


    /**
     * 分页查询角色信息
     * @param page      分页信息
     * @param roleDTO   查询条件
     * @return  返回角色信息
     * @throws Exception   异常抛出
     */
    Page<Role> queryRoles(Page<Role> page, RoleDTO roleDTO) throws Exception;

    /**
     * 更新角色信息
     * @param roleDTO       角色传输类
     * @throws Exception    更新时异常
     */
    void updateRole(RoleDTO roleDTO) throws Exception;

    /**
     * 根据角色ID删除角色信息 以及 角色相关的 关联表
     * @param roleId        角色ID
     * @throws Exception    异常上抛
     */
    void delRole(String roleId) throws Exception;

    /**
     * 批量删除角色
     * @param roleIds 要删除的角色ID
     */
    void delRole(String[] roleIds) throws Exception ;

    /**
     * 查询角色对应的权限
     * @param roleId    角色ID
     */
    List<Function> queryRolePermissions(String roleId);

}
