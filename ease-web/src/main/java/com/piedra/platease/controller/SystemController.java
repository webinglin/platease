package com.piedra.platease.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
     * 系统登录
     * @return  返回登录之后的页面
     */
    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView();
        try{

        } catch(Exception e){
            e.printStackTrace();
        }
        mav.setViewName("system/login");
        return mav;
    }






}
