package com.coursework.oneWay.controllers;

import com.coursework.oneWay.RequestStatus;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.*;
import com.coursework.oneWay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.EnumSet;

@Controller
@RequestMapping(value = "/requests")
public class RequestController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private RequestService requestService;
    @Autowired
    private TourService tourService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TourOperatorService tourOperatorService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RequestPassportService requestPassportService;

    @GetMapping
    public String requests(Model model){
        model.addAttribute("requestAdmit",
                requestService.findAdmitted(httpSessionBean.getConnection()));
        model.addAttribute("requestUnadmit",
                requestService.findUnadmitted(httpSessionBean.getConnection()));
        model.addAttribute("status_values", EnumSet.allOf(RequestStatus.class));
        return "requests";
    }
    @GetMapping("/{requestId}")
    public String requestDetails(@PathVariable int requestId, Model model){

        Request request = requestService.findById(requestId, httpSessionBean.getConnection());
        Client client = clientService.findById(request.getClientId(), httpSessionBean.getConnection());

        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("request", request);
        model.addAttribute("client", client);
        model.addAttribute("balance", client.getPersonalWallet().getBalance());
        model.addAttribute("passports", passportService.findByRequestId(requestId,
                httpSessionBean.getConnection()));
        model.addAttribute("hotels", hotelService.findByTourId(request.getTourId(),
                httpSessionBean.getConnection()));

        return "request-details";
    }

    @PostMapping("/admit/{id}")
    public String requestsAdmit(@RequestParam(name = "status") String status,
                                @PathVariable(name = "id") int id){
        requestService.setStatus(id, status.toLowerCase(), httpSessionBean.getId(), httpSessionBean.getConnection());
        return "redirect:/requests/";
    }

    @PostMapping("/delete/{id}")
    public String requestDeleteCheck(@PathVariable int id){
        requestService.deleteById(id, httpSessionBean.getConnection());
        return "redirect:/requests";
    }

    @PostMapping("/sendDocuments/{requestId}")
    public String sendDocuments(@PathVariable int requestId) {
        Request request = requestService.findById(requestId, httpSessionBean.getConnection());
        Client client = clientService.findById(request.getClientId(), httpSessionBean.getConnection());

        try {
            mailSenderService.sendMailToTourOperator(
                    tourOperatorService.findByRequestId(requestId, httpSessionBean.getConnection()).getEmail(),
                    passportService.findByRequestId(requestId, httpSessionBean.getConnection()),
                    tourService.findByRequestId(requestId, httpSessionBean.getConnection()));

            requestService.setStatus(requestId, RequestStatus.БРОНЮВАННЯ.toDBFormat(),
                    httpSessionBean.getId(), httpSessionBean.getConnection());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return "redirect:/requests/{requestId}";
    }

    @PostMapping("/{requestId}/saveTravelDocuments")
    public String saveTravelDocuments(@PathVariable int requestId,
                                      @RequestParam(name = "passportId") int passportId,
                                      @RequestParam MultipartFile[] tickets,
                                      @RequestParam MultipartFile[] vouchers,
                                      @RequestParam MultipartFile insurance){

        requestPassportService.saveClientDocuments(requestId, passportId, tickets, vouchers, insurance,
                httpSessionBean.getConnection());
        return "redirect:/requests/{requestId}";
    }

    @PostMapping("/{requestId}/sendTravelDocuments")
    public String sendTravelDocuments(@PathVariable int requestId){
        Client client = clientService.findByRequestId(requestId, httpSessionBean.getConnection());

        try {
            mailSenderService.sendMailToClientWithTravelDocuments(client.getEmail(), requestId,
                    httpSessionBean.getConnection());

            requestService.setStatus(requestId, RequestStatus.ПРИЙНЯТО.toDBFormat(),
                    httpSessionBean.getId(), httpSessionBean.getConnection());
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return "redirect:/requests/{requestId}";
    }
}