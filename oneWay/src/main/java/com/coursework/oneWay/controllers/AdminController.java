package com.coursework.oneWay.controllers;

import com.coursework.oneWay.WorkerRole;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.Worker;
import com.coursework.oneWay.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.EnumSet;

@Controller
@RequestMapping(value = "/adminPanel")
public class AdminController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private WorkerService workerService;

    @GetMapping
    public String adminPanel(Model model){
        model.addAttribute("workerList", workerService.findAll(httpSessionBean.getConnection()));
        model.addAttribute("roles", EnumSet.allOf(WorkerRole.class));
        return "admin-panel";
    }

    @PostMapping("/createWorker")
    public String createWorker(Worker worker, @RequestParam String password){
        workerService.save(worker, httpSessionBean.getConnection());
        return "redirect:/adminPanel";
    }
}
