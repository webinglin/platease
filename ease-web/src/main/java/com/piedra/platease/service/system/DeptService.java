package com.piedra.platease.service.system;

import com.piedra.platease.dto.DeptDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Dept;
import com.piedra.platease.service.BaseService;

/**
 * @author webinglin
 * @since 2017-05-03
 */
public interface DeptService extends BaseService<Dept> {

    /**
     * 更新单位信息
     * @param deptDTO   单位传输对象
     * @throws Exception    往上层抛异常
     */
    void updateDept(DeptDTO deptDTO) throws Exception;

    /**
     * 分页查询单位信息
     * @param page      分页信息
     * @param deptDTO   单位信息传输对象
     * @return  返回分页结果
     * @throws Exception    往上层抛异常
     */
    Page<Dept> queryDeptList(Page<Dept> page, DeptDTO deptDTO) throws Exception;

}
