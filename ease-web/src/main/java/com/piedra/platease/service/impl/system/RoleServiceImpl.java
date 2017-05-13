package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.RoleDao;
import com.piedra.platease.dto.RoleDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.RoleService;
import com.piedra.platease.utils.BeanMapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 角色实体业务实现层
 * @author webinglin
 * @since 2017-04-05
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    public void setBaseDao(RoleDao roleDao) {
        super.setBaseDao(roleDao);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateRoleFunctions(String roleId, Set<String> newFuncIds) throws Exception {
        if(newFuncIds==null){
            return ;
        }

        // 角色原来已经拥有的所有权限
        List<Function> funcs = roleDao.queryRolePermissions(roleId);

        Set<String> originalFuncIds = new HashSet<>();
        funcs.forEach(func -> originalFuncIds.add(func.getId()));

        // 两者都有的，不需要改变的角色ID集合
        Collection<String> intersectionCollection = CollectionUtils.intersection(originalFuncIds, newFuncIds);

        // 需要新增的角色权限
        newFuncIds.removeAll(intersectionCollection);

        // 需要删掉的角色权限
        originalFuncIds.removeAll(intersectionCollection);

        if(CollectionUtils.isNotEmpty(newFuncIds)) {
            roleDao.addRoleFuncs(roleId, newFuncIds);
        }
        if(CollectionUtils.isNotEmpty(originalFuncIds)) {
            roleDao.deleteRoleFuncs(roleId, originalFuncIds);
        }
    }

    /**
     * 分页查询角色信息
     *
     * @param page    分页信息
     * @param roleDTO 查询条件
     * @return 返回角色信息
     * @throws Exception 异常抛出
     */
    @Override
    public Page<Role> queryRoles(Page<Role> page, RoleDTO roleDTO) throws Exception {
        Map<String,Object> params = BeanMapUtil.trans2Map(roleDTO);
        return roleDao.queryByNameWithTotal(page, "SysRole.queryRoleListCnt", "SysRole.queryRoleList", params);
    }

    /**
     * 更新角色信息
     *
     * @param roleDTO 角色传输类
     * @throws Exception 更新时异常
     */
    @Override
    public void updateRole(RoleDTO roleDTO) throws Exception {
        if(roleDTO==null){
            return ;
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        roleDao.updateRole(role);
    }


    @Override
    public void delRole(String roleId) throws Exception {
        roleDao.delete(roleId);
        Map<String,Object> params = Collections.singletonMap("roleId", roleId);
        roleDao.executeQueryByName("SysRole.delUserRoleByRoleId",params);
        roleDao.executeQueryByName("SysRole.delRoleFuncByRoleId",params);
    }

    @Override
    public void delRole(String[] roleIds) throws Exception {
        if(roleIds == null || roleIds.length==0){
            return ;
        }

        for(String roleId : roleIds){
            this.delRole(roleId);
        }
    }

    @Override
    public List<Function> queryRolePermissions(String roleId) {
        return roleDao.queryRolePermissions(roleId);
    }
}
