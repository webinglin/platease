package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.FunctionDao;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.service.impl.BaseServiceImpl;
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
public class FunctionServiceImpl extends BaseServiceImpl<Function> implements FunctionService {

    @Autowired
    private FunctionDao funcDao;
    @Autowired
    public void setBaseDao(FunctionDao funcDao) {
        super.setBaseDao(funcDao);
    }






}
