package com.coursework.oneWay.controllers;

import com.coursework.oneWay.STATUS;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.EnumSet;

@Controller
public class RequestController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private RequestService requestService;

    @GetMapping("/requests")
    public String requests(Model model){
        model.addAttribute("requestAdmit",
                requestService.findAdmitted(httpSessionBean.getConnection()));
        model.addAttribute("requestUnadmit",
                requestService.findUnadmitted(httpSessionBean.getConnection()));
        model.addAttribute("status_values", EnumSet.allOf(STATUS.class));
        return "requests";
    }

    @PostMapping("/requests/admit/{id}")
    public String requestsAdmit(@RequestParam(name = "status") String status,
                                @PathVariable(name = "id") int id){
        System.out.println("status = " + status);
        System.out.println("id = " + id);

        requestService.setStatus(id, status.toLowerCase(), httpSessionBean.getConnection());
        return "redirect:/requests/";
    }

    @GetMapping("/requests/delete/{id}")
    public String requestDeleteCheck(@PathVariable int id){
        requestService.deleteById(id, httpSessionBean.getConnection());
        return "redirect:/requests";
    }

//    @PostMapping("/request/{tourId}-{clientId}")
//    public String requestCreate(@PathVariable(name = "tourId") int tourId,
//                                @PathVariable(name = "clientId") int clientId){
//        requestService.saveNew(clientId, tourId);
//        return "redirect:/requests/";
//    }
}