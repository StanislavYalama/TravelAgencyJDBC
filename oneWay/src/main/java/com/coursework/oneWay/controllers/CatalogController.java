package com.coursework.oneWay.controllers;

import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.Location;
import com.coursework.oneWay.models.Tour;
import com.coursework.oneWay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
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

    @GetMapping("/catalog")
    public String catalog(Model model) throws SQLException {

        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("tours",
                tourService.findAll(httpSessionBean.getConnection()));

        httpSessionBean.setLastUrl("redirect:/catalog");
        return "catalog";
    }
    @GetMapping("/catalog/{tourId}")
    public String catalogTourDetails(Model model, @PathVariable int tourId){
        model.addAttribute("tour",
                tourService.findById(tourId, httpSessionBean.getConnection()));
        model.addAttribute("locations",
                locationService.findByTourId(tourId, httpSessionBean.getConnection()));
        model.addAttribute("clientId", httpSessionBean.getId());
        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("promotions",
                promotionService.findByTourId(tourId, httpSessionBean.getConnection()));
        model.addAttribute("allLocations", locationService.finAll(httpSessionBean.getConnection()));

        httpSessionBean.setLastUrl("redirect:/catalog/".concat(Integer.toString(tourId)));
        return "tour-details";
    }
    @PostMapping("/catalog/save")
    public String catalogSave(Tour tour){
        tour.setCreatorId(httpSessionBean.getId());
        tourService.save(tour, httpSessionBean.getConnection());
        return "redirect:/catalog";
    }
    @PostMapping("/catalog/delete/{tourId}")
    public String catalogDelete(@PathVariable int tourId){
        tourService.deleteById(tourId, httpSessionBean.getConnection());
        return "redirect:/catalog";
    }
    @PostMapping("/catalog/{tourId}/send-request/{clientId}")
    public String sendRequest(@PathVariable int tourId,
                              @PathVariable int clientId){
        requestService.save(clientId, tourId, httpSessionBean.getConnection());
        return "redirect:/catalog/{tourId}";
    }
    @PostMapping("/catalog/addPromotion/{tourId}")
    public String catalogAddPromotion(@PathVariable int tourId, @RequestParam int promotionId){
        promotionService.linkWithTour(promotionId, tourId, httpSessionBean.getConnection());
        return "redirect:/catalog/{tourId}";
    }

    @PostMapping("/catalog/addLocation/{tourId}")
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

    @PostMapping("/catalog/{tourId}/deleteLocation/{locationId}")
    public String catalogDeleteLocation(@PathVariable int tourId, @PathVariable int locationId){
        tourService.deleteLocation(tourId, locationId, httpSessionBean.getConnection());
        return "redirect:/catalog/{tourId}";
    }
}