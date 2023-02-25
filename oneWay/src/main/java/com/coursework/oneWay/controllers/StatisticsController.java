package com.coursework.oneWay.controllers;

import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private MatViewService matViewService;

    @GetMapping("/tour")
    public String tourStatistics(Model model){
        Map<Integer, Integer> fTourRankMap = new TreeMap<>();
        Map<Integer, BigDecimal> fTourProfitMap = new TreeMap<>();

        matViewService.showTourRank(httpSessionBean.getConnection()).
                forEach(x-> fTourRankMap.put(x.getId(), x.getRequest()));
        matViewService.showTourProfit(httpSessionBean.getConnection()).
                forEach(x-> fTourProfitMap.put(x.getId(), x.getProfit()));

        model.addAttribute("tourPopularity", fTourRankMap);
        model.addAttribute("tourProfit", fTourProfitMap);
        return "statistics-tour";
    }
    @GetMapping("/tour/refresh")
    public String tourStatisticsRefresh(){
        matViewService.refreshTourRank(httpSessionBean.getConnection());
        matViewService.refreshTourProfit(httpSessionBean.getConnection());
        return "redirect:/statistics/tour";
    }
    @GetMapping("/client")
    public String clientStatistics(Model model){
        Map<String, Integer> fClientRankMap = new TreeMap<>();
        matViewService.showClientRank(httpSessionBean.getConnection()).
                forEach((x) -> fClientRankMap.put(x.getClientName(), x.getCountRequestAdmit()));
        model.addAttribute("clientRank", fClientRankMap);

        System.out.println("Map: " + fClientRankMap);
        return "statistics-client";
    }
    @GetMapping("/client/refresh")
    public String clientStatisticsRefresh(){
        matViewService.refreshClientRank(httpSessionBean.getConnection());
        return "redirect:/statistics/client";
    }

    @GetMapping("/worker")
    public String workerStatistics(Model model){
        Map<String, Integer> fManagerRankMap4R = new TreeMap<>();
        Map<String, BigDecimal> fManagerRankMap4P = new TreeMap<>();
        Map<String, Integer> fTourManagerRankMap = new TreeMap<>();

        matViewService.showManagerRank(httpSessionBean.getConnection()).
                forEach(x-> fManagerRankMap4R.put(x.getName(), x.getRequests()));
        matViewService.showManagerRank(httpSessionBean.getConnection()).
                forEach(x-> fManagerRankMap4P.put(x.getName(), x.getProfit()));
        matViewService.showTourManagerRank(httpSessionBean.getConnection()).
                forEach(x-> fTourManagerRankMap.put(x.getName(), x.getCreatedTours()));

        model.addAttribute("managerRankRequests", fManagerRankMap4R);
        model.addAttribute("managerRankProfit", fManagerRankMap4P);
        model.addAttribute("tourManagerRank", fTourManagerRankMap);
        return "statistics-worker";
    }

    @GetMapping("/worker/refresh")
    public String workerStatisticsRefresh(){
        matViewService.refreshManagerRank(httpSessionBean.getConnection());
        matViewService.refreshTourManagerRank(httpSessionBean.getConnection());
        return "redirect:/statistics/worker";
    }
}