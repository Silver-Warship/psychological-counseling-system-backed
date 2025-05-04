package org.example.psychologicalcounseling.controller;


import org.example.psychologicalcounseling.module.OrderManage.getArrangementInfo.ArrangementInfoResponse;
import org.example.psychologicalcounseling.module.OrderManage.getArrangementInfo.ArrangementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api")
public class ArrangementInfoController {

    @Autowired
    private ArrangementInfoService arrangementInfoService;

    @GetMapping("/getArrangeOrderInTime")
    public ArrangementInfoResponse getArrangeOrderInTime(Long startTimestamp, Long endTimestamp, String role) {

        return arrangementInfoService.getArrangementInfo(startTimestamp, endTimestamp, role);

    }

}
