package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.DeptDao;
import com.piedra.platease.model.system.Dept;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.DetpService;
import com.piedra.platease.service.system.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 功能函数实体业务实现层
 * @author webinglin
 * @since 2017-05-03
 */
@Service
@Transactional
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DetpService {

    @Autowired
    private DeptDao deptDao;
    @Autowired
    public void setBaseDao(DeptDao deptDao) {
        super.setBaseDao(deptDao);
    }






}
