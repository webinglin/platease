package com.piedra.platease.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 单位表控制层
 * @author webinglin
 * @since 2017-04-06
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @RequestMapping("/index")
    public String index(){
        return "dept/dept_index";
    }






}
