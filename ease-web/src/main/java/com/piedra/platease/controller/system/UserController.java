package com.piedra.platease.controller.system;

import com.piedra.platease.constants.Constants;
import com.piedra.platease.constants.DateConstants;
import com.piedra.platease.constants.StatusConstants;
import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.entity.ResultModel;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.Dept;
import com.piedra.platease.model.system.Function;
import com.piedra.platease.model.system.Role;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.system.DeptService;
import com.piedra.platease.service.system.RoleService;
import com.piedra.platease.service.system.UserService;
import com.piedra.platease.utils.CollectionUtil;
import com.piedra.platease.utils.Md5Util;
import com.piedra.platease.utils.PasswordUtil;
import com.piedra.platease.utils.UUIDUtil;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 系统用户控制层
 * @author webinglin
 * @since 2017-04-06
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;

    @RequestMapping("/index")
    public String index(){
        return "system/user/user_index";
    }


    /**
     * 查询用户
     * @param page    分页信息
     * @param userDto 用户数据--过滤条件
     * @return  返回结果
     */
    @RequestMapping("/queryUsers")
    @ResponseBody
    public Page<User> queryUsers(Page<User> page, UserDTO userDto){
        try {
            page.setEntityClass(User.class);

            if(StringUtils.isNotBlank(userDto.getSearchCont())){
                userDto.setDeptCode(null);  // 搜索的不限制
                userDto.setSearchCont(Constants.PERCENT + userDto.getSearchCont() + Constants.PERCENT);
            }

            page = userService.queryByPage(page, userDto);

            // 设置单位
//            List<User> users = page.getDatas();
//            for(User u : users){
//                // TODO 单位缓存, 获取单位ID
//            }

        } catch(Exception e){
            logger.error("查询用户失败", e);
        }
        return page;
    }

    /**
     * 根据用户ID查询用户信息
     * @param userDto   用户数据DTO
     * @return  返回用户信息
     */
    @RequestMapping("/queryUserById")
    @ResponseBody
    public UserDTO queryUserById(UserDTO userDto){
        if(StringUtils.isBlank(userDto.getId())) {
            return userDto;
        }
        try {
            User u = userService.get(userDto.getId());
            BeanUtils.copyProperties(u, userDto);
            // 设置单位名称
            if(StringUtils.isNotBlank(u.getDeptId())) {
                Dept dept = deptService.get(u.getDeptId());
                if(dept!=null && StringUtils.isNotBlank(dept.getDeptName())) {
                    userDto.setDeptName(dept.getDeptName());
                }
            }
            // 设置角色
            List<Role> roles = userService.queryUserRoles(userDto.getId());
            if(CollectionUtils.isNotEmpty(roles)) {
                Set<String> roleNames = new HashSet<>();
                roles.forEach(role -> roleNames.add(role.getRoleName()));
                userDto.setRoleNames(CollectionUtil.join(roleNames, Constants.PAUSE));
            }
            // 设置创建者名称
            if(StringUtils.isNotBlank(u.getCreatorId())) {
                User creator = userService.get(u.getCreatorId());
                if(creator!=null && StringUtils.isNotBlank(creator.getRealName())) {
                    userDto.setCreatorName(creator.getRealName());
                }
            }
            // 设置创建时间
            Date createTime = u.getCreateTime();
            if(createTime!=null) {
                userDto.setCreateTimeStr(DateFormatUtils.format(createTime, DateConstants.YMD_HMS));
            }
            // 设置最后登录时间
            Date lastLoginTime = u.getLastLoginTime();
            if(lastLoginTime!=null) {
                userDto.setLastLoginTimeStr(DateFormatUtils.format(lastLoginTime, DateConstants.YMD_HMS));
            }
        } catch(Exception e){
            logger.error("查询用户失败", e);
        }
        return userDto;
    }


    /**
     * 添加用户
     * @param userDto 新增的用户数据
     * @return  返回结果
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public ResultModel addUser( UserDTO userDto){
        ResultModel resultModel = new ResultModel();
        try {
            userService.addUser(userDto);
        } catch(Exception e){
            logger.error("添加用户失败", e);
        }
        return resultModel;
    }

    /**
     * 删除用户
     * @param userDto 要删除的用户
     * @return  返回结果
     */
    @RequestMapping("/delUser")
    @ResponseBody
    public ResultModel delUser(UserDTO userDto){
        ResultModel resultModel = new ResultModel();
        String userId = userDto.getId();
        if(StringUtils.isBlank(userId)) {
            return resultModel.setError("请选择需要删除的用户");
        }
        try {
            userService.delUsers(userId.split(Constants.COMMA));
        } catch(Exception e){
            logger.error("删除用户失败", e);
        }
        return resultModel;
    }

    /**
     * 修改用户
     * @param userDto 修改的用户数据
     * @return  返回结果
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public ResultModel updateUser( UserDTO userDto){
        ResultModel resultModel = new ResultModel();
        if(userDto==null || StringUtils.isBlank(userDto.getId())) {
            return resultModel.setError("更新的用户ID不能为空");
        }
        try {

            userService.updateUser(userDto);

        } catch(Exception e){
            logger.error("修改用户失败", e);
        }
        return resultModel;
    }

    /**
     * 查询用户拥有的角色
     * @param userId    用户ID
     * @return      返回角色列表
     */
    @RequestMapping("/queryUserRoles")
    @ResponseBody
    public List<Role> queryUserRoles(String userId){
        List<Role> roleList = new ArrayList<>();
        try {
            roleList = userService.queryUserRoles(userId);
        } catch(Exception e){
            logger.error("查询角色列表出错", e);
        }
        return roleList;
    }


}
