package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.FunctionDao;
import com.piedra.platease.dto.FunctionDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.FunctionService;
import com.piedra.platease.utils.BeanMapUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;


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


    @Override
    public void updateFunction(FunctionDTO functionDTO) throws Exception {
        Function func = new Function();
        BeanUtils.copyProperties(functionDTO,func);
        funcDao.updateFunc(func);
    }

    @Override
    public Page<Function> queryFunctionList(Page<Function> page, FunctionDTO functionDTO) throws Exception {
        Map<String,Object> params = BeanMapUtil.trans2Map(functionDTO);
        return funcDao.queryByNameWithTotal(page, "SysFunc.queryFuncListCnt", "SysFunc.queryFuncList", params);
    }

    @Override
    public void delFunction(String funcId) throws Exception {
        funcDao.delete(funcId);
        Map<String,Object> params = Collections.singletonMap("funcId", funcId);
        funcDao.executeQueryByName("SysFunc.delRoleFuncByFuncId",params);
    }
}
