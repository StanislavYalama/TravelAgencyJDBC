package com.coursework.oneWay.controllers;

import com.coursework.oneWay.bean.HttpSessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private HttpSessionBean httpSessionBean;

    @GetMapping("/")
    public String home(){

        httpSessionBean.setLastUrl("redirect:/");
        return "home";
    }
}
