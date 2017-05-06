package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.RoleDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.utils.BeanMapUtil;
import com.piedra.platease.utils.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
    private static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public List<Function> queryRolePermissions(String roleId) {
        Query query = getSession().createNamedQuery("SysRole.queryRoleFuncs").setParameter("roleId", roleId);
        return query.list();
    }

    @Override
    public void addRoleFuncs(String roleId, Set<String> newFuncIds) {
        Map<String,Object> params = new HashMap<>();
        params.put("roleId",roleId);
        newFuncIds.forEach(funcId -> {
            params.put("funcId", funcId);
            executeQueryByName("SysRole.addRoleFunc", params);
        });
    }

    @Override
    public void deleteRoleFuncs(String roleId, Set<String> delFuncIds) {
        Map<String,Object> params = new HashMap<>();
        params.put("roleId",roleId);
        params.put("funcIds", delFuncIds);
        executeQueryByName("SysRole.delRoleFunc", params);
    }

    @Override
    public void updateRole(Role role) {
        if(role==null || StringUtils.isBlank(role.getId())){
            logger.warn("角色、角色ID为空，无法更新");
            return ;
        }
        executeQueryByName("SysRole.updateRole", BeanMapUtil.trans2Map(role));
    }
}
