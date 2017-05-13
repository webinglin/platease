package com.piedra.platease.controller.system;

import com.piedra.platease.constants.Constants;
import com.piedra.platease.constants.WebConstants;
import com.piedra.platease.dto.FunctionDTO;
import com.piedra.platease.entity.ResultModel;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.service.system.FunctionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能菜单控制层
 * @author webinglin
 * @since 2017-04-06
 */
@Controller
@RequestMapping("/func")
public class FunctionController {
    private static Logger logger = LoggerFactory.getLogger(FunctionController.class);

    @Autowired
    private FunctionService functionService;

    @RequestMapping("/index")
    public String index(){
        return "system/func/func_index";
    }


    /**
     * 查询权限列表   -- 权限管理的树zNodes来源一样
     * @param page     分页参数
     * @param dto       数据传输对象
     * @return  返回分页数据
     */
    @RequestMapping("/queryFuncs")
    @ResponseBody
    public Page<Function> queryFuncs(Page<Function> page, FunctionDTO dto){
        try {
            page.setEntityClass(Function.class);

            if(StringUtils.isNotBlank(dto.getSearchCont())){
                dto.setParentId(null);  // 搜索的不限制
                dto.setSearchCont(Constants.PERCENT + dto.getSearchCont() + Constants.PERCENT);
            }

            page = functionService.queryFunctionList(page, dto);

            // 来源构造树的请求
            if(WebConstants.FROM_TREE.equals(dto.getFrom())){
                Function rootFunc = new Function();
                rootFunc.setId(Constants.PARENT_ID);
                rootFunc.setFuncTitle("菜单权限");
                page.getDatas().add(rootFunc);
            }
        } catch (Exception e){
            logger.error("权限查询失败", e);
        }
        return page;
    }

    /**
     * 添加权限
     * @param functionDTO   权限
     * @return  返回添加反馈结果
     */
    @RequestMapping("/addFunc")
    @ResponseBody
    public ResultModel addFunc(FunctionDTO functionDTO){
        ResultModel resultModel = new ResultModel();
        try {
            Function func = functionService.addFunction(functionDTO);
            resultModel.setData(func);
        } catch(Exception e){
            resultModel.setError("添加权限出错:" + e.getMessage());
            logger.error("添加权限出错", e);
        }
        return resultModel;
    }

    /**
     * 删除权限
     * @param functionDTO   权限
     * @return  返回添加反馈结果
     */
    @RequestMapping("/delFunc")
    @ResponseBody
    public ResultModel delFunc(FunctionDTO functionDTO){
        ResultModel resultModel = new ResultModel();
        if(functionDTO==null || StringUtils.isBlank(functionDTO.getId())){
            return resultModel.setError("要删除的权限ID不能为空");
        }
        try {
            functionService.delFunctions(functionDTO.getId().split(Constants.COMMA));
        } catch(Exception e){
            resultModel.setError("删除权限出错:" + e.getMessage());
            logger.error("删除权限出错", e);
        }
        return resultModel;
    }


    /**
     * 修改权限
     * @param functionDTO   权限
     * @return  返回添加反馈结果
     */
    @RequestMapping("/updateFunc")
    @ResponseBody
    public ResultModel updateFunc(FunctionDTO functionDTO){
        ResultModel resultModel = new ResultModel();
        try {
            functionService.updateFunction(functionDTO);
        } catch(Exception e){
            resultModel.setError("修改权限出错:" + e.getMessage());
            logger.error("修改权限出错", e);
        }
        return resultModel;
    }
}
