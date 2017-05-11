package com.piedra.platease.controller.system;

import com.piedra.platease.constants.Constants;
import com.piedra.platease.constants.SessionConstants;
import com.piedra.platease.dto.FunctionDTO;
import com.piedra.platease.dto.RoleDTO;
import com.piedra.platease.entity.FuncLegend;
import com.piedra.platease.entity.ResultModel;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.service.system.FunctionService;
import com.piedra.platease.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 角色控制层
 * @author webinglin
 * @since 2017-04-06
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private FunctionService functionService;

    @RequestMapping("/index")
    public String index(ModelMap modelMap){
        try {
            Page<Function> page = new Page<>(1, Integer.MAX_VALUE);
            page.setEntityClass(Function.class);
            page.setSidx("ORDER_STR");
            page.setSord(Constants.Order.ASC);
            List<Function> funcs = functionService.queryFunctionList(page, null).getDatas();

            // 构造符合前端的数据格式
            Map<String, FuncLegend> legendMap = new LinkedHashMap<>();
            for(Function func : funcs){
                if(Constants.PARENT_ID.equals(func.getParentId())){
                    FuncLegend legend = new FuncLegend();
                    legend.setLegend(func.getFuncTitle());
                    legend.getFuncs().add(func);
                    legendMap.put(func.getId(), legend);
                    continue ;
                }
                legendMap.get(func.getParentId()).getFuncs().add(func);
            }
            List<FuncLegend> legends = new ArrayList<>();
            legendMap.values().forEach(funcLegend -> legends.add(funcLegend));
            modelMap.put("funcLegends", legends);

        } catch (Exception e) {
            logger.error("加载所有的权限出错", e);
        }
        return "role/role_index";
    }

    /**
     * 分页查询角色信息
     * @param page      分页信息
     * @param roleDTO   查询条件
     * @return  返回分页结果
     */
    @RequestMapping("/queryRoles")
    @ResponseBody
    public Page<Role> queryRoles(Page<Role> page, RoleDTO roleDTO){
        try {
            page.setEntityClass(Role.class);
            page = roleService.queryRoles(page,roleDTO);
        } catch (Exception e){
            logger.error("查询角色分页信息出错", e);
        }
        return page;
    }

    /**
     * 查询角色相关联的所有权限
     * @param roleId    角色ID
     * @return  返回角色相关的权限列表
     */
    @RequestMapping("/queryRoleFuncs")
    @ResponseBody
    public List<String> queryRoleFuncs(String roleId){
        List<String> funcIdList = new ArrayList<>();
        try {
            List<Function> roleFuncs = roleService.queryRolePermissions(roleId);
            roleFuncs.forEach(func -> funcIdList.add(func.getId()));
        } catch (Exception e){
            logger.error("查询角色权限出错",e);
        }
        return funcIdList;
    }

    /**
     * 修改角色-权限 映射关系（为角色分配权限）
     * @param roleId    角色ID
     * @param funcIds   权限ID列表
     * @return  返回 角色-权限 映射关系
     */
    @RequestMapping("/updateRoleFuncs")
    @ResponseBody
    public ResultModel updateRoleFuncs(String roleId, String funcIds ){
        ResultModel result = new ResultModel();
        if(StringUtils.isBlank(roleId)){
            return result.setError("要修改的角色ID为空");
        }
        try {
            Set<String> funcIdSet = new HashSet<>();
            if(StringUtils.isNotBlank(funcIds)){
                String[] funcIdArr = funcIds.split(Constants.COMMA);
                for(String funcId : funcIdArr){
                    funcIdSet.add(funcId);
                }
            }

            roleService.updateRoleFunctions(roleId, funcIdSet);

        } catch (Exception e){
            result.setError(e.getMessage());
            logger.error("为角色分配权限出错", e);
        }
        return result;
    }

}
