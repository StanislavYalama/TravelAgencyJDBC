package com.coursework.oneWay.controllers;

import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.Tour;
import com.coursework.oneWay.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

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
    @Autowired
    private DocumentService documentService;

    @GetMapping("/catalog")
    public String catalog(Model model) throws SQLException {

        model.addAttribute("role", httpSessionBean.getRole());
        model.addAttribute("tours",
                tourService.findAll(httpSessionBean.getConnection()));
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
        model.addAttribute("documents",
                documentService.findTourDocumentByTourId(tourId, httpSessionBean.getConnection()));
        return "tour-details";
    }
    /*
    * @RequestParam(name = "clientId") int clientId,
                                  @RequestParam(name = "status") String status,
                                  @RequestParam(name = "dateBeginning") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateBeggining,
                                  @RequestParam(name = "dateEnd") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateEnd,
                                  @RequestParam(name = "tourId") int tourId,
                                  @RequestParam(name = "payment") boolean paymentStatus,
                                  * */
    @PostMapping("/catalog/save")
    public String catalogSave(Tour tour){
        tourService.save(tour, httpSessionBean.getConnection());
        return "redirect:/catalog";
    }
    @PostMapping("catalog/delete/{tourId}")
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
    @PostMapping("catalog/linking/{tourId}")
    public String catalogLink(@PathVariable int tourId, @RequestParam int promotionId){
        promotionService.linkWithTour(promotionId, tourId, httpSessionBean.getConnection());
        return "redirect:/catalog/{tourId}";
    }
}