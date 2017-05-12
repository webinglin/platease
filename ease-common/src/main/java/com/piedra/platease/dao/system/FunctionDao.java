package com.piedra.platease.dao.system;


import com.piedra.platease.dao.BaseDao;
import com.piedra.platease.model.system.Function;

/**
 * Dao操作接口定义
 * @author webinglin
 * @since 2017-05-03
 */
public interface FunctionDao extends BaseDao<Function> {

    /**
     * 更新权限
     * @param func  权限
     */
    void updateFunc(Function func) throws Exception;

    /**
     * 根据父节点ID删除权限
     * @param funcId    权限ID
     */
    void deleteByParentId(String funcId)  throws Exception;
}
