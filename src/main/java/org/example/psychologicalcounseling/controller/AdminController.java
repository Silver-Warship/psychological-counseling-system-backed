package org.example.psychologicalcounseling.controller;

import org.example.psychologicalcounseling.module.OrderManage.getAllOrder.GetAllOrderNumberResponse;
import org.example.psychologicalcounseling.module.OrderManage.getAllOrder.GetOrderService;
import org.example.psychologicalcounseling.utils.TimeStampUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    private final GetOrderService getOrderService;

    public AdminController(GetOrderService getOrderService) {
        this.getOrderService = getOrderService;
    }

    @GetMapping("/api/getAllOrderNumber")
    public ResponseEntity<?> getAllOrderNumber(Long startTimestamp, Long endTimestamp, Long timeStep) {
        // check if startTimestamp and endTimestamp are valid
        var timeStampError = TimeStampUtil.timestampBetweenCheck(startTimestamp, endTimestamp);
        if (timeStampError.isPresent()) {
            return ResponseEntity.badRequest().body(timeStampError);
        }

        if (timeStep == null) {
            timeStep = endTimestamp - startTimestamp;
        }

        int orderNumCnt = (int) ((endTimestamp - startTimestamp) / timeStep);
        GetAllOrderNumberResponse response = new GetAllOrderNumberResponse();
        response.setOrderList(new GetAllOrderNumberResponse.OrderNum[orderNumCnt]);
        for (int i=1; i<=orderNumCnt; i++) {
            // Get the order number for the current timestamp
            Long currentTimestamp = startTimestamp + (i - 1) * timeStep;
            var orderNum = getOrderService.getOrderNumber(currentTimestamp, timeStep + currentTimestamp);
            // add the order number to the response
            response.getOrderList()[i - 1] = orderNum;
        }

        return response.buildResponse();
    }
}
