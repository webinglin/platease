package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.RoleDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
        if(CollectionUtils.isEmpty(newFuncIds)){
            return ;
        }

        // 角色原来已经拥有的所有权限
        List<Function> funcs = roleDao.queryRolePermissions(roleId);

        Set<String> originalFuncIds = new HashSet<>();
        funcs.forEach(func -> originalFuncIds.add(func.getFuncId()));

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
}
