package com.coursework.oneWay.controllers;

import com.coursework.oneWay.STATUS;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.*;
import com.coursework.oneWay.services.ClientService;
import com.coursework.oneWay.services.MailSenderService;
import com.coursework.oneWay.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.EnumSet;

@Controller
public class RequestController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MailSenderService mailSenderService;

    @GetMapping("/requests")
    public String requests(Model model){
        model.addAttribute("requestAdmit",
                requestService.findAdmitted(httpSessionBean.getConnection()));
        model.addAttribute("requestUnadmit",
                requestService.findUnadmitted(httpSessionBean.getConnection()));
        model.addAttribute("status_values", EnumSet.allOf(STATUS.class));
        return "requests";
    }
    @GetMapping("/requests/{requestId}")
    public String requestDetails(@PathVariable int requestId, Model model){

        Request request = requestService.findById(requestId, httpSessionBean.getConnection());
        Client client = clientService.findById(request.getClientId(), httpSessionBean.getConnection());

        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("request", request);
        model.addAttribute("client", client);
        model.addAttribute("balance", client.getPersonalWallet().getBalance());
        return "request-details";
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

    // TODO
    @PostMapping("/requests/sendDocuments/{requestId}")
    public String sendDocuments(@PathVariable int requestId) throws MessagingException {
        Request request = requestService.findById(requestId, httpSessionBean.getConnection());
        Client client = clientService.findById(request.getClientId(), httpSessionBean.getConnection());

        mailSenderService.sendMailToTourOperator(client.getEmail(), client.getName());
        return "redirect:/requests/{requestId}";
    }

    @PostMapping("/requests/sendTravelDocuments/{requestId}")
    public String sendTravelDocuments(@PathVariable int requestId,
                                      @RequestParam MultipartFile multipartFile){


        return "redirect:/requests/{requestId}";
    }
}