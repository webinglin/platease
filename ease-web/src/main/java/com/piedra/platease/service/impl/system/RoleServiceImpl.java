package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.RoleDao;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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






}
