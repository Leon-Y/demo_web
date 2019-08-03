package com.example.web.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: 36560
 * @Date: 2019/5/30 :6:40
 * @Description:
 */
@Controller
@RequestMapping("/")
public class Test {
    @RequestMapping("/test")
    @ResponseBody
    public String getResult(){
        return "我是一个应用页面";
    }

}
