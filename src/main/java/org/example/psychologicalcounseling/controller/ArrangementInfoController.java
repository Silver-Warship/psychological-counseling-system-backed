package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.OrderManage.getArrangementInfo.ArrangementInfoResponse;
import org.example.psychologicalcounseling.module.OrderManage.getArrangementInfo.ArrangementInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ArrangementInfoController {
    private final ArrangementInfoService arrangementInfoService;

    public ArrangementInfoController(ArrangementInfoService arrangementInfoService) {
        this.arrangementInfoService = arrangementInfoService;
    }
    /**
     * 获取一段时间内的所有排班信息
     * @param startTimestamp  开始时间戳
     * @param endTimestamp    结束时间戳
     * @param role            角色
     * @return 该时间段内咨当前询师和督导的排班信息
     */
    @GetMapping("/getArrangeOrderInTime")
    public ArrangementInfoResponse getArrangeOrderInTime(Long startTimestamp, Long endTimestamp, String role) {
        return arrangementInfoService.getArrangementInfo(startTimestamp, endTimestamp, role);
    }
}
