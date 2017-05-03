package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.RoleDao;
import com.piedra.platease.model.system.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
    private static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);


    
}
