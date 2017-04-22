package com.piedra.platease.controller.system;

import com.piedra.platease.entity.ResultModel;
import com.piedra.platease.model.system.User;
import com.piedra.platease.service.system.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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
     * @param user  新增的用户数据
     * @return  返回结果
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public ResultModel addUser(HttpServletRequest req, User user){
        ResultModel resultModel = new ResultModel();
        try {
            user = new User();
            user.setId(UUID.randomUUID().toString().replaceAll("-",""));
            user.setUserName("test01");
            user.setTelphone("189..");
            byte b = 1;
            user.setStatus(b);
            user.setRealName("测试");
            user.setRemark("备注备注");

            userService.save(user);

        } catch(Exception e){
            logger.error("添加用户失败", e);
        }

        return resultModel;
    }






}
