package com.zyq.community.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zyq
 * @description
 * @create 2021/5/5
 **/
@Controller
public class indexController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
