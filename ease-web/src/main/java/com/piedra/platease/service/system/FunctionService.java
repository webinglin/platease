package com.piedra.platease.service.system;

import com.piedra.platease.dto.FunctionDTO;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.service.BaseService;

/**
 * @author webinglin
 * @since 2017-05-03
 */
public interface FunctionService extends BaseService<Function> {

    /**
     * 更新权限
     * @param functionDTO   更新权限
     * @throws Exception    异常往上一层抛出
     */
    void updateFunction(FunctionDTO functionDTO) throws Exception;

    /**
     * 分页查询权限列表
     * @param page          分页信息
     * @param functionDTO   权限传输对象
     * @return  返回分页结果
     * @throws Exception    异常往上一层抛出
     */
    Page<Function> queryFunctionList(Page<Function> page, FunctionDTO functionDTO) throws Exception;

    /**
     * 根据权限ID 删除权限信息以及 关联表
     * @param funcId        权限ID
     * @throws Exception    异常上抛
     */
    void delFunction(String funcId) throws Exception;

}
