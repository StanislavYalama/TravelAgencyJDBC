package com.coursework.oneWay.controllers;

import com.coursework.oneWay.STATUS;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.*;
import com.coursework.oneWay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
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
    private PassportService passportService;

    @GetMapping("/cabinet/{id}")
    public String cabinet(Model model, @PathVariable int id){
        Client client = clientService.findById(id, httpSessionBean.getConnection());
        model.addAttribute("client", client);
        model.addAttribute("balance", client.getPersonalWallet().getBalance());
        model.addAttribute("request", requestService.findByClientId(id,
                httpSessionBean.getConnection()).stream().sorted(Comparator.comparing(Request::getDate)).collect(Collectors.toList()));
        model.addAttribute("status_values", EnumSet.allOf(STATUS.class));
        model.addAttribute("voucher", voucherService.findByClientId(id,
                httpSessionBean.getConnection()));
        model.addAttribute("role", httpSessionBean.getRole());
        return "clients-cabinet";
    }

    @PostMapping("/cabinet/{clientId}/payRequest/{requestId}")
    public String requestPay(@PathVariable int clientId, @PathVariable int requestId){
        requestService.pay(requestId, clientId, httpSessionBean.getConnection());
        return "redirect:/cabinet/{clientId}";
    }

    @PostMapping("/cabinet/{clientId}/addPassport")
    public String addPassport(@PathVariable int clientId, @RequestParam Passport passport){
        passportService.save(passport, httpSessionBean.getConnection());
        clientService.updatePassportId(clientId, passport.getId(), httpSessionBean.getConnection());
        return "redirect:/cabinet/{clientId}";
    }
}