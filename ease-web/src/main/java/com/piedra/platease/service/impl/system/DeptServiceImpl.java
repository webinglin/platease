package com.piedra.platease.service.impl.system;

import com.piedra.platease.dao.system.DeptDao;
import com.piedra.platease.dto.DeptDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Dept;
import com.piedra.platease.service.impl.BaseServiceImpl;
import com.piedra.platease.service.system.DeptService;
import com.piedra.platease.utils.BeanMapUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * 功能函数实体业务实现层
 * @author webinglin
 * @since 2017-05-03
 */
@Service
@Transactional
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {

    @Autowired
    private DeptDao deptDao;
    @Autowired
    public void setBaseDao(DeptDao deptDao) {
        super.setBaseDao(deptDao);
    }


    @Override
    public void updateDept(DeptDTO deptDTO) throws Exception {
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptDTO, dept);
        deptDao.updateDept(dept);
    }

    @Override
    public Page<Dept> queryDeptList(Page<Dept> page, DeptDTO deptDTO) throws Exception {
        Map<String,Object> params = BeanMapUtil.trans2Map(deptDTO);
        return deptDao.queryByNameWithTotal(page, "SysDept.queryDeptListCnt", "SysUser.queryDeptList", params);
    }

    /**
     * 删除单位 逻辑删除
     *
     * @param deptId 单位ID
     * @throws Exception 异常上抛
     */
    @Override
    public void deleteDept(String deptId) throws Exception {

    }
}
