package com.coursework.oneWay.controllers;

import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login/authorization")
    public String loginAuth(@RequestParam (name = "name") String name,
                            @RequestParam (name = "password") String password) throws SQLException {
        httpSessionBean.getConnection().close();
        httpSessionBean.setConnection(loginService.getConnection(name, password));
        httpSessionBean.setId(loginService.getUserId(name, httpSessionBean.getConnection()));
        httpSessionBean.setRole(loginService.getRole(name, httpSessionBean.getConnection()));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() throws SQLException {
        httpSessionBean.getConnection().close();
        httpSessionBean.setConnection(loginService.getConnection("guest", "guest"));
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationProcess(Client client){
        loginService.createUser(client, httpSessionBean.getConnection());
        return "redirect:/login";
    }
}