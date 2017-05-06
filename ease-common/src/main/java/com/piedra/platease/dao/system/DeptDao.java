package com.piedra.platease.dao.system;


import com.piedra.platease.dao.BaseDao;
import com.piedra.platease.model.system.Dept;

/**
 * 单位Dao操作接口定义
 * @author webinglin
 * @since 2017-05-03
 */
public interface DeptDao extends BaseDao<Dept> {

    /**
     * 更新单位信息
     * @param dept  单位信息
     */
    void updateDept(Dept dept);
}
