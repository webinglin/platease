package com.piedra.platease.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统控制层
 * 包含系统登录，系统退出等接口
 * @author webinglin
 * @since 2017-04-03
 */
@Controller
@RequestMapping("/system")
public class SystemController {


    /**
     * 系统管理页面
     */
    @RequestMapping(value="/manage")
    public String manage(){
        // TODO 获取一系列的个性化菜单配置   Request域即可

        return "/system/manage";
    }

    /**
     * 平台介绍页面
     * @return
     */
    @RequestMapping(value="/intro")
    public String intro(){
        return "/system/intro";
    }

    /**
     * 未授权
     */
    @RequestMapping(value="/unAuth")
    public String unAuth(){
        return "error/unauth";
    }


}
