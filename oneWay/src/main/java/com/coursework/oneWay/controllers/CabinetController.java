package com.coursework.oneWay.controllers;

import com.coursework.oneWay.RequestStatus;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.*;
import com.coursework.oneWay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cabinet")
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

    @GetMapping("/{id}")
    public String cabinet(Model model, @PathVariable int id){
        Client client = clientService.findById(id, httpSessionBean.getConnection());
        double balance = clientService.getPersonalWalletByClinetId(client.getId(), httpSessionBean.getConnection())
                .getBalance();
        balance = Math.floor(balance * 100) / 100;

        model.addAttribute("userId", httpSessionBean.getId());
        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("client", client);
        model.addAttribute("balance", balance);
        model.addAttribute("request", requestService.findByClientId(id,
                httpSessionBean.getConnection()).stream().sorted(Comparator.comparing(Request::getDate)).collect(Collectors.toList()));
        model.addAttribute("status_values", EnumSet.allOf(RequestStatus.class));
        model.addAttribute("voucher", voucherService.findByClientId(id,
                httpSessionBean.getConnection()));
        return "clients-cabinet";
    }

    @PostMapping("/{clientId}/changeBalance")
    public String changeBalance(@PathVariable int clientId, @RequestParam double newBalance){
        clientService.changeBalanceByClientId(clientId, newBalance, httpSessionBean.getConnection());
        return "redirect:/cabinet/{clientId}";
    }

    @PostMapping("/{clientId}/payRequest/{requestId}")
    public String requestPay(@PathVariable int clientId, @PathVariable int requestId){
        requestService.pay(requestId, clientId, httpSessionBean.getConnection());
        return "redirect:/cabinet/{clientId}";
    }

    @PostMapping("/{clientId}/denyRequest/{requestId}")
    public String requestDeny(@PathVariable int clientId, @PathVariable int requestId){
        String newStatus = switch (httpSessionBean.getRole()) {
            case "manager" -> RequestStatus.СКАСОВАНО_АГЕНСТВОМ.toDBFormat();
            case "client" -> RequestStatus.СКАСОВАНО_КЛІЄНТОМ.toDBFormat();
            default -> "";
        };

        if(!newStatus.equals("")){
            Request request = requestService.findById(requestId, httpSessionBean.getConnection());
            requestService.setStatus(requestId, newStatus, httpSessionBean.getId(),
                    httpSessionBean.getConnection());
        }

        return "redirect:/cabinet/{clientId}";
    }

    @PostMapping("/{clientId}/deleteRequest/{requestId}")
    String deleteRequest(@PathVariable(name = "clientId") int clientId, @PathVariable(name = "requestId") int requestId){
        requestService.deleteById(requestId, httpSessionBean.getConnection());
        return "redirect:/cabinet/{clientId}";
    }
}