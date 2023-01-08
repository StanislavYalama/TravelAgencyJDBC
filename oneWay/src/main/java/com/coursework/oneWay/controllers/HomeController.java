package com.coursework.oneWay.controllers;

import com.coursework.oneWay.bean.HttpSessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private HttpSessionBean httpSessionBean;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("userId", httpSessionBean.getId());

        httpSessionBean.setLastUrl("redirect:/");
        return "home";
    }
}
