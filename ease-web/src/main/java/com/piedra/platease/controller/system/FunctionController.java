package com.piedra.platease.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能菜单控制层
 * @author webinglin
 * @since 2017-04-06
 */
@Controller
@RequestMapping("/func")
public class FunctionController {
    private static Logger logger = LoggerFactory.getLogger(FunctionController.class);

    @RequestMapping("/index")
    public String index(){
        return "func/func_index";
    }


}
