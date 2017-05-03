package com.piedra.platease.controller.system;

import com.piedra.platease.constants.StatusConstants;
import com.piedra.platease.dto.UserDTO;
import com.piedra.platease.entity.ResultModel;
import com.piedra.platease.model.Page;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.system.UserService;
import com.piedra.platease.utils.PasswordUtil;
import com.piedra.platease.utils.UUIDUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    @RequestMapping("/index")
    public String index(){
        return "user/user_index";
    }

    /**
     * 添加用户
     * @param req   HttpRequest请求对象
     * @param userDto 新增的用户数据
     * @return  返回结果
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public ResultModel addUser(HttpServletRequest req, UserDTO userDto){
        ResultModel resultModel = new ResultModel();
        try {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);

            user.setId(UUIDUtil.generateUUID());
            user.setSalt(RandomStringUtils.random(8));
            // 对密码再度进行加盐HASH
            user.setPassword(PasswordUtil.encryptPassword(user.getPassword(), user.getSalt()));
            user.setStatus(StatusConstants.USER_AVAILABLE);
            user.setCreateTime(new Date());

            // TODO 根据deptid 获取先关的单位CODE
//            user.setDeptCode();

            userService.save(user);
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
            return resultModel.setError("需要指定删除用户的ID");
        }
        try {
            userService.delete(userId);
        } catch(Exception e){
            logger.error("删除用户失败", e);
        }
        return resultModel;
    }

    /**
     * 修改用户
     * @param req   HttpRequest请求对象
     * @param userDto 修改的用户数据
     * @return  返回结果
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public ResultModel updateUser(HttpServletRequest req, UserDTO userDto){
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
     * 查询用户
     * @param page  分页信息
     * @param userDto 用户数据--过滤条件
     * @return  返回结果
     */
    @RequestMapping("/queryUsers")
    @ResponseBody
    public Page<User> queryUsers(Page<User> page, UserDTO userDto){
        try {
            userService.queryByPage(page, userDto);
        } catch(Exception e){
            logger.error("查询用户失败", e);
        }
        return page;
    }

}
