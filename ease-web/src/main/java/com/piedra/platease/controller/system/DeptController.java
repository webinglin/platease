package com.piedra.platease.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(DeptController.class);

    @RequestMapping("/index")
    public String index(){
        return "dept/dept_index";
    }






}
