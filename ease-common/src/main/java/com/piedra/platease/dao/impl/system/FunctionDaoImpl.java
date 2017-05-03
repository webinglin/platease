package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.FunctionDao;
import com.piedra.platease.model.system.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {
    private static Logger logger = LoggerFactory.getLogger(FunctionDaoImpl.class);


    
}
