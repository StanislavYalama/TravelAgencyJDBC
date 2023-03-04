package com.coursework.oneWay.controllers;

import com.coursework.oneWay.DocumentType;
import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.*;
import com.coursework.oneWay.services.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Controller
@RequestMapping(value = "/catalog")
public class CatalogController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private TourService tourService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private RequestPassportService requestPassportService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ExcursionService excursionService;
    @Autowired
    private DocumentService documentService;

    @GetMapping
    public String catalog(Model model) throws SQLException {

        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("tours",
                tourService.findAll(httpSessionBean.getConnection()));
        model.addAttribute("allLocations", locationService.finAll(httpSessionBean.getConnection()));
        model.addAttribute("documentTypes", EnumSet.allOf(DocumentType.class));


        httpSessionBean.setLastUrl("redirect:/catalog");
        return "catalog";
    }
    @GetMapping("/{tourId}")
    public String catalogTourDetails(Model model, @PathVariable int tourId){
        List<Location> locationList = locationService.findByTourId(tourId, httpSessionBean.getConnection());
        model.addAttribute("tour",
                tourService.findById(tourId, httpSessionBean.getConnection()));
        model.addAttribute("locations", locationList);
        model.addAttribute("clientId", httpSessionBean.getId());
        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("promotions",
                promotionService.findByTourId(tourId, httpSessionBean.getConnection()));
        model.addAttribute("allLocations", locationService.finAll(httpSessionBean.getConnection()));

        List<Excursion> excursionList = excursionService.findByTourId(tourId, httpSessionBean.getConnection());
        model.addAttribute("excursionList", excursionList);
        model.addAttribute("excursionsGson", new Gson().toJson(excursionList));
        model.addAttribute("documentList", documentService.findAll(httpSessionBean.getConnection()));
        model.addAttribute("tourDocumentList",
                documentService.findTourDocumentByTourId(tourId, httpSessionBean.getConnection()));

        httpSessionBean.setLastUrl("redirect:/catalog/".concat(Integer.toString(tourId)));
        return "tour-details";
    }
    @PostMapping("/save")
    public String catalogSave(Tour tour){
        tour.setCreatorId(httpSessionBean.getId());
        tourService.save(tour, httpSessionBean.getConnection());
        return "redirect:/catalog";
    }
    @PostMapping("/delete/{tourId}")
    public String catalogDelete(@PathVariable int tourId){
        tourService.deleteById(tourId, httpSessionBean.getConnection());
        return "redirect:/catalog";
    }

    @PostMapping("/{tourId}/addMembers")
    public String catalogAddMembersPage(@PathVariable(name = "tourId") int tourId,
                                        @RequestParam(name = "members_count") int members_count, Model model){

        if(members_count == 1){
            return "redirect:/catalog/" + tourId + "/addOneMember/processing";
        }
        model.addAttribute("tourId", tourId);
        model.addAttribute("members_count", members_count);
        model.addAttribute("passport", passportService.findById(
                clientService.findById(httpSessionBean.getId(),
                        httpSessionBean.getConnection()).getPassportId(),
                httpSessionBean.getConnection()));
        model.addAttribute("new_passport", new Passport());
        return "form-members";
    }

    @GetMapping("/{tourId}/addOneMember/processing")
    public String catalogAddOneMember(@PathVariable(name = "tourId") int tourId){
        int requestId = requestService.save(httpSessionBean.getId(), tourId, httpSessionBean.getConnection());
        Client client = clientService.findById(httpSessionBean.getId(), httpSessionBean.getConnection());

        requestPassportService.save(new RequestPassport(0, requestId, client.getPassportId()),
                httpSessionBean.getConnection());

        return "redirect:/cabinet/" + client.getId();
    }

    @PostMapping("/{tourId}/addMembers/{members_count}/processing")
    public String catalogAddMembers(@PathVariable(name = "tourId") int tourId,
                                    @RequestParam(name = "name") List<String> nameList,
                                    @RequestParam(name = "documentNumber") List<String> documentNumberList,
                                    @RequestParam(name = "dateOfExpiry") List<String> dateOfExpiryList,
                                    @RequestParam(name = "dateOfIssue") List<String> dateOfIssueList,
                                    @PathVariable(name = "members_count") int membersCount){
        int requestId = requestService.save(httpSessionBean.getId(), tourId, httpSessionBean.getConnection());
        Client client = clientService.findById(httpSessionBean.getId(), httpSessionBean.getConnection());

        requestPassportService.save(new RequestPassport(0, requestId,
                clientService.findById(httpSessionBean.getId(), httpSessionBean.getConnection()).getPassportId()),
                httpSessionBean.getConnection());

        for(int i = 0; i < membersCount - 1; i++){
            Passport passport = new Passport(
                    0,
                    nameList.get(i),
                    documentNumberList.get(i),
                    LocalDate.parse(dateOfExpiryList.get(i)),
                    LocalDate.parse(dateOfIssueList.get(i))
            );
            int passportId = passportService.save(passport, httpSessionBean.getConnection());
            requestPassportService.save(new RequestPassport(0, requestId, passportId), httpSessionBean.getConnection());
        }

        return "redirect:/cabinet/" + client.getId();
    }

    @PostMapping("/addPromotion/{tourId}")
    public String catalogAddPromotion(@PathVariable int tourId, @RequestParam int promotionId){
        promotionService.linkWithTour(promotionId, tourId, httpSessionBean.getConnection());
        return "redirect:/catalog/{tourId}";
    }

    @PostMapping("/addLocation/{tourId}")
    public String catalogAddLocation(@PathVariable int tourId,
                                      @RequestParam(name = "location") List<Integer> locationIdList){
        List<Location> locationList = new ArrayList<>();
        locationIdList.forEach(el ->
        {
            if(el != 0){
                locationList.add(locationService.findById(el, httpSessionBean.getConnection()));
            }
        });

        if(!locationList.isEmpty()){
            tourService.saveLocations(locationList, tourId, httpSessionBean.getConnection());
        }

        return "redirect:/catalog/{tourId}";
    }

    @PostMapping("/{tourId}/deleteLocation/{locationId}")
    public String catalogDeleteLocation(@PathVariable int tourId, @PathVariable int locationId){
        tourService.deleteLocation(tourId, locationId, httpSessionBean.getConnection());
        return "redirect:/catalog/{tourId}";
    }

    @PostMapping("/{tourId}/addExcursion")
    public String addExcursion(@PathVariable int tourId,
                               @RequestParam(name = "addExcursion_excursion") int excursionId){

        tourService.saveExcursion(tourId, excursionId, httpSessionBean.getConnection());
        return "redirect:/catalog/{tourId}";
    }

    @PostMapping("/{tourId}/addDocument")
    public String addDocument(@PathVariable int tourId,
                              @RequestParam(name = "documentIdList") List<Integer> documentIdList){

        System.out.println(documentIdList);
        if(!documentIdList.isEmpty()){
            System.out.println(documentIdList.get(0));
            List<TourDocument> tourDocumentList = new ArrayList<>();
            documentIdList.forEach(el -> tourDocumentList.add(new TourDocument(0, tourId, el)));
            documentService.saveTourDocumentList(tourDocumentList, httpSessionBean.getConnection());
        }

        return "redirect:/catalog/{tourId}";
    }

    @PostMapping("/saveLocation")
    public String saveLocation(Location location){
        location.setCreatorId(httpSessionBean.getId());
        locationService.save(location, httpSessionBean.getConnection());
        return httpSessionBean.getLastUrl();
    }

    @PostMapping("/saveExcursion")
    public String saveExcursion(Excursion excursion){
        excursionService.save(excursion, httpSessionBean.getConnection());
        return httpSessionBean.getLastUrl();
    }

    @PostMapping("/saveDocument")
    public String saveDocument(Document document){
        documentService.save(document, httpSessionBean.getConnection());
        return httpSessionBean.getLastUrl();
    }
}