package com.zyq.community.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zyq
 * @description
 * @create 2021/5/2
 **/
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}