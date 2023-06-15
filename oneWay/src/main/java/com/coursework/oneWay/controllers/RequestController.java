package com.coursework.oneWay.controllers;

import com.coursework.oneWay.enums.DocumentType;
import com.coursework.oneWay.enums.RequestStatus;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.*;
import com.coursework.oneWay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

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
    private MailSenderService mailSenderService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private MultipartFileUtils multipartFileUtils;

    @Value("${upload.path}")
    String toClientDocumentPackage;
    @Value("${uploadTravelDoc.path}")
    private String uploadTravelDocPath;

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
        double balance = clientService.getPersonalWalletByClinetId(client.getId(), httpSessionBean.getConnection())
                .getBalance();
        balance = Math.floor(balance * 100) / 100;

        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("request", request);
        model.addAttribute("client", client);
        model.addAttribute("balance", balance);
        model.addAttribute("hotels", hotelService.findByTourId(request.getTourId(),
                httpSessionBean.getConnection()));
        model.addAttribute("members_count",
                requestService.getMembersCountById(requestId,httpSessionBean.getConnection()));
        model.addAttribute("requiredTourDocumentList",
                documentService.findTourDocumentByTourId(request.getTourId(), httpSessionBean.getConnection())
                .stream().filter(el -> el.getType().equals(DocumentType.ДЛЯ_УЧАСТІ_В_ТУРІ.toDBFormat())).toList());

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

        try {
            mailSenderService.sendMailToTourOperator(
                    tourOperatorService.findByRequestId(requestId, httpSessionBean.getConnection()).getEmail(),
                    requestId,
                    tourService.findByRequestId(requestId, httpSessionBean.getConnection()));

            requestService.setStatus(requestId, RequestStatus.БРОНЮВАННЯ.toDBFormat(),
                    httpSessionBean.getId(), httpSessionBean.getConnection());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return "redirect:/requests/{requestId}";
    }

    @PostMapping("/{requestId}/sendTravelDocuments")
    public String saveTravelDocuments(@PathVariable int requestId,
                                      @RequestParam(name = "requiredDocuments")
                                              MultipartFile[] requiredDocuments){

        Request request = requestService.findById(requestId, httpSessionBean.getConnection());
        Client client = clientService.findByRequestId(requestId, httpSessionBean.getConnection());
        int membersCount = requestService.getMembersCountById(requestId, httpSessionBean.getConnection());

        if(requiredDocuments != null){
            List<TourDocumentView> requiredTourDocuments =
                    documentService.findTourDocumentByTourId(request.getTourId(), httpSessionBean.getConnection())
                            .stream().filter(el -> el.getType().equals(DocumentType.ДЛЯ_УЧАСТІ_В_ТУРІ.toDBFormat())).toList();

            int k = 0;
            for(int i = 0; i < membersCount; i++){
                for(TourDocumentView el : requiredTourDocuments){
                    String toFilePath = "request" + requestId + "/member" + (i + 1) + "/";
                    String fullToFilePath = uploadTravelDocPath +  toFilePath;

                    try {
                        String subStr = uploadTravelDocPath.substring(0, uploadTravelDocPath.length() - 1);
                        String filePathDB = subStr.substring(subStr.lastIndexOf('/')) + "/" +
                                toFilePath +
                                multipartFileUtils.uploadFile(requiredDocuments[k], fullToFilePath, el.getName());

                        documentService.saveRequestTourDocument(
                                new RequestTourDocument(0, requestId, el.getTourDocumentId(), filePathDB, i + 1),
                                httpSessionBean.getConnection());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    k++;
                }
            }

            try {
                mailSenderService.sendMailToClientWithTravelDocuments(client.getEmail(), requestId);

                requestService.setStatus(requestId, RequestStatus.ПРИЙНЯТО.toDBFormat(),
                        httpSessionBean.getId(), httpSessionBean.getConnection());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/requests/{requestId}";
    }
}