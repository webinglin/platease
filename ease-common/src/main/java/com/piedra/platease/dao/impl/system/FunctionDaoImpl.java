package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.FunctionDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.utils.BeanMapUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {
    private static Logger logger = LoggerFactory.getLogger(FunctionDaoImpl.class);


    @Override
    public void updateFunc(Function func) throws Exception {
        if(func==null || StringUtils.isBlank(func.getId())){
            logger.warn("权限、权限ID为空，无法更新");
            return ;
        }
        executeQueryByName("SysFunc.updateFunc", BeanMapUtil.trans2Map(func));
    }

    @Override
    public void deleteByParentId(String funcId) throws Exception {
        executeQueryByName("SysFunc.deleteByParentId", Collections.singletonMap("parentId",funcId));
    }

}
