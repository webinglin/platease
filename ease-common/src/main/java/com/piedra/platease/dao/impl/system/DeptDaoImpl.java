package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.DeptDao;
import com.piedra.platease.model.system.Dept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DeptDaoImpl extends BaseDaoImpl<Dept> implements DeptDao {
    private static Logger logger = LoggerFactory.getLogger(DeptDaoImpl.class);



}
