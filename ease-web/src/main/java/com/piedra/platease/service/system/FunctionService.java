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
     * 根据ID删除菜单权限
     * 如果是 0.....[32] 为顶级节点ID，提示抛出异常，不允许删除，只能通过数据库脚本删除
     * 如果ID不是0....0 那么可以删除，删除的时候除了删除自身，还需要将parentId为该ID的也一起删除
     * @param funcId    菜单ID
     * @throws Exception    异常上抛
     */
    void delFunction(String funcId) throws Exception;

    /**
     * 添加权限
     * @param functionDTO   权限
     */
    Function addFunc(FunctionDTO functionDTO) throws Exception;
}
