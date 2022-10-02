package com.coursework.oneWay.controllers;

import com.coursework.oneWay.STATUS;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.Request;
import com.coursework.oneWay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Controller
public class CabinetController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private ClientService clientService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private TourService tourService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private DocumentService documentService;

    @GetMapping("/cabinet/{id}")
    public String cabinet(Model model, @PathVariable int id){
        model.addAttribute("client", clientService.findById(id,
                httpSessionBean.getConnection()));
        model.addAttribute("request", requestService.findByClientId(id,
                httpSessionBean.getConnection()).stream().sorted(Comparator.comparing(Request::getDate)).collect(Collectors.toList()));
        model.addAttribute("status_values", EnumSet.allOf(STATUS.class));
        model.addAttribute("voucher", voucherService.findByClientId(id,
                httpSessionBean.getConnection()));
        model.addAttribute("role", httpSessionBean.getRole());
//        model.addAttribute("documents",
//                documentService.findClientDocumentByClientId(id, httpSessionBean.getConnection()));
        return "clients-cabinet";
    }

    @PostMapping("/cabinet/{clientId}/payRequest/{requestId}")
    public String requestPay(@PathVariable int clientId, @PathVariable int requestId){
        requestService.pay(requestId, clientId, httpSessionBean.getConnection());
        return "redirect:/cabinet/{clientId}";
    }

    @GetMapping("/personal/cabinet/{id}")
    public String personalCabinet(Model model, @PathVariable int id){
        model.addAttribute("client", clientService.findById(id,
                httpSessionBean.getConnection()));
        return "personal-cabinet";
    }
}
