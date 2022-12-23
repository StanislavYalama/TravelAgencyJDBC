package com.coursework.oneWay.controllers;

import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.models.Promotion;
import com.coursework.oneWay.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class PromotionController {

    @Autowired
    private PromotionService promotionService;
    @Autowired
    private HttpSessionBean httpSessionBean;

    @GetMapping("/promotions")
    public String promotionList(Model model){
        model.addAttribute("promotion",
                promotionService.findAll(httpSessionBean.getConnection()));
        model.addAttribute("role", httpSessionBean.getRole());

        httpSessionBean.setLastUrl("redirect:/promotions");
        return "promotion-list";
    }

    @GetMapping("/promotions/{promotionId}")
    public String promotionDetails(Model model, @PathVariable int promotionId){
        model.addAttribute("promotion",
                promotionService.findById(promotionId, httpSessionBean.getConnection()));
        model.addAttribute("role", httpSessionBean.getRole());

        httpSessionBean.setLastUrl("redirect:/promotions/".concat(Integer.toString(promotionId)));
        return "promotion-details";
    }

    @PostMapping("/promotions/create")
    public String promotionCreate(@RequestParam(name = "dateBeginning") String dateBeginning,
                                  @RequestParam(name = "dateEnd") String dateEnd,
                                  @RequestParam(name = "discountPercentage") int discountPercentage){
        dateBeginning = dateBeginning.replace("T", " ");
        dateEnd = dateEnd.replace("T", " ");
        Promotion promotion =
                new Promotion(0,
                        LocalDateTime.parse(dateBeginning, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        LocalDateTime.parse(dateEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        httpSessionBean.getId(),
                        discountPercentage);
        promotionService.save(promotion, httpSessionBean.getConnection());
        return "redirect:/promotions";
    }

    @PostMapping("/promotions/delete/{promotionId}")
    public String promotionDelete(@PathVariable int promotionId){
        promotionService.delete(promotionId, httpSessionBean.getConnection());
        return "redirect:/promotions";
    }

    @PostMapping("promotions/linking/{promotionId}")
    public String promotionLinking(@PathVariable int promotionId,
                                   @RequestParam(name = "tourId") int tourId){
        promotionService.linkWithTour(promotionId, tourId, httpSessionBean.getConnection());
        return "redirect:/promotions/{promotionId}";
    }
}
