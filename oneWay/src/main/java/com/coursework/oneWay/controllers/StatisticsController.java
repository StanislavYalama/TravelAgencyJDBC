package com.coursework.oneWay.controllers;

import com.coursework.oneWay.bean.HttpSessionBean;
import com.coursework.oneWay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class StatisticsController {

    @Autowired
    private HttpSessionBean httpSessionBean;
    @Autowired
    private FunctionsService functionsService;

    @GetMapping("/statistics/tour/")
    public String tourStatistics(Model model){
        Map<Integer, Integer> fTourRankMap = new TreeMap<>();
        Map<Integer, BigDecimal> fTourProfitMap = new TreeMap<>();

        functionsService.callTourRank(httpSessionBean.getConnection()).
                forEach(x-> fTourRankMap.put(x.getId(), x.getRequest()));
        functionsService.callTourProfit(httpSessionBean.getConnection()).
                forEach(x-> fTourProfitMap.put(x.getId(), x.getProfit()));

        model.addAttribute("tourPopularity", fTourRankMap);
        model.addAttribute("tourProfit", fTourProfitMap);
        return "statistics-tour";
    }
    @GetMapping("/statistics/client/")
    public String clientStatistics(Model model){
        Map<String, Integer> fClientRankMap = new TreeMap<>();
        functionsService.callClientRank(httpSessionBean.getConnection()).
                forEach((x) -> fClientRankMap.put(x.getClientName(), x.getCountVisits()));
        model.addAttribute("clientRank", fClientRankMap);

        System.out.println("Map: " + fClientRankMap);
        return "statistics-client";
    }
    @GetMapping("/statistics/worker/")
    public String workerStatistics(Model model){
        Map<String, Integer> fManagerRankMap4R = new TreeMap<>();
        Map<String, BigDecimal> fManagerRankMap4P = new TreeMap<>();
        Map<String, Integer> fTourManagerRankMap = new TreeMap<>();

        functionsService.callManagerRank(httpSessionBean.getConnection()).
                forEach(x-> fManagerRankMap4R.put(x.getName(), x.getRequests()));
        functionsService.callManagerRank(httpSessionBean.getConnection()).
                forEach(x-> fManagerRankMap4P.put(x.getName(), x.getProfit()));
        functionsService.callTourManagerRank(httpSessionBean.getConnection()).
                forEach(x-> fTourManagerRankMap.put(x.getName(), x.getCreatedTours()));

        model.addAttribute("managerRankRequests", fManagerRankMap4R);
        model.addAttribute("managerRankProfit", fManagerRankMap4P);
        model.addAttribute("tourManagerRank", fTourManagerRankMap);
        return "statistics-worker";
    }
}