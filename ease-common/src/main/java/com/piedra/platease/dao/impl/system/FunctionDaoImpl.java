package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.FunctionDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.utils.BeanMapUtil;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {
    private static Logger logger = LoggerFactory.getLogger(FunctionDaoImpl.class);


    @Override
    public void updateFunc(Function func) {
        if(func==null || StringUtils.isBlank(func.getId())){
            logger.warn("权限、权限ID为空，无法更新");
            return ;
        }
        executeQueryByName("SysFunc.updateFunc", BeanMapUtil.trans2Map(func));
    }
}
