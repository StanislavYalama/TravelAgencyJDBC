package com.coursework.oneWay.controllers;

import com.coursework.oneWay.enums.WorkerRole;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.Client;
import com.coursework.oneWay.services.ClientService;
import com.coursework.oneWay.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class LoginController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ClientService clientService;

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
        if(httpSessionBean.getRole().equals(WorkerRole.ADMINISTRATOR.toDBFormat())){
            loginService.setDBRole(WorkerRole.ADMINISTRATOR.toDBFormat(), httpSessionBean.getConnection());
            return "redirect:/adminPanel";
        }
        return httpSessionBean.getLastUrl();
    }

    @GetMapping("/logout")
    public String logout() throws SQLException {
        httpSessionBean.getConnection().close();
        httpSessionBean.setConnection(loginService.getConnection("guest", "guest"));
        httpSessionBean.setRole("guest");
        httpSessionBean.setId(0);
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        if(httpSessionBean.getRole().equals("guest")){
            return "registration";
        }
        return httpSessionBean.getLastUrl();
    }

    @PostMapping("/registration")
    public String registrationProcess(Client client,
                                      @RequestParam("password") String password) {
        loginService.registerClient(client, password, httpSessionBean.getConnection());
        return "redirect:/login";
    }
}