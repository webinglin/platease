package com.piedra.platease.dao.impl.system;

import com.piedra.platease.dao.impl.BaseDaoImpl;
import com.piedra.platease.dao.system.DeptDao;
import com.piedra.platease.model.system.Dept;
import com.piedra.platease.utils.BeanMapUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;

@Repository
public class DeptDaoImpl extends BaseDaoImpl<Dept> implements DeptDao {
    private static Logger logger = LoggerFactory.getLogger(DeptDaoImpl.class);


    @Override
    public void updateDept(Dept dept) {
        if(dept==null || StringUtils.isBlank(dept.getId())){
            logger.warn("单位、单位ID为空，无法更新");
            return ;
        }
        executeQueryByName("SysDept.updateDept", BeanMapUtil.trans2Map(dept));

    }
}
