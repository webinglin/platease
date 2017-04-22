package com.piedra.platease.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 角色控制层
 * @author webinglin
 * @since 2017-04-06
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @RequestMapping("/index")
    public String index(){
        return "role/role_index";
    }


}
