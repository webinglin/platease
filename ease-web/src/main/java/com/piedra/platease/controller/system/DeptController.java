package com.piedra.platease.controller.system;

import com.piedra.platease.constants.Constants;
import com.piedra.platease.constants.WebConstants;
import com.piedra.platease.dto.DeptDTO;
import com.piedra.platease.entity.ResultModel;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Dept;
import com.piedra.platease.service.system.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 单位表控制层
 * @author webinglin
 * @since 2017-04-06
 */
@Controller
@RequestMapping("/dept")
public class DeptController {
    private static Logger logger = LoggerFactory.getLogger(DeptController.class);

    @RequestMapping("/index")
    public String index(){
        return "system/dept/dept_index";
    }

    @Autowired
    private DeptService deptService;


    /**
     * 查询单位
     * @param page     分页参数
     * @param dto       数据传输对象
     * @return  返回分页数据
     */
    @RequestMapping("/queryDepts")
    @ResponseBody
    public Page<Dept> queryDepts(Page<Dept> page, DeptDTO dto){
        try {
            page.setEntityClass(Dept.class);
            page = deptService.queryDeptList(page, dto);

            // 来源构造树的请求
            if(WebConstants.FROM_TREE.equals(dto.getFrom())){
                Dept rootDept = new Dept();
                rootDept.setId(Constants.PARENT_ID);
                rootDept.setDeptName("单位");
                page.getDatas().add(rootDept);
            }
        } catch (Exception e){
            logger.error("权限单位失败", e);
        }
        return page;
    }

    /**
     * 添加单位
     * @param dto   权限
     * @return  返回添加反馈结果
     */
    @RequestMapping("/addDept")
    @ResponseBody
    public ResultModel addDept(DeptDTO dto){
        ResultModel resultModel = new ResultModel();
        try {
            Dept dept = deptService.addDept(dto);
            resultModel.setData(dept);
        } catch(Exception e){
            resultModel.setError("添加单位出错:" + e.getMessage());
            logger.error("添加单位出错", e);
        }
        return resultModel;
    }

    /**
     * 删除单位
     * @param dto   权限
     * @return  返回添加反馈结果
     */
    @RequestMapping("/delDept")
    @ResponseBody
    public ResultModel delDept(DeptDTO dto){
        ResultModel resultModel = new ResultModel();
        if(dto==null || StringUtils.isBlank(dto.getId())){
            return resultModel.setError("要删除的单位ID不能为空");
        }
        try {
            deptService.delete(dto.getId());
        } catch(Exception e){
            resultModel.setError("删除单位出错:" + e.getMessage());
            logger.error("删除单位出错", e);
        }
        return resultModel;
    }



}
