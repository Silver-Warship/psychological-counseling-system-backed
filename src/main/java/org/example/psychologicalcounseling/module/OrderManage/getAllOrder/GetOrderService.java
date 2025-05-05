package org.example.psychologicalcounseling.module.OrderManage.getAllOrder;

import org.example.psychologicalcounseling.repository.CounsellorArrangementRepository;
import org.example.psychologicalcounseling.repository.SupervisorArrangementRepository;
import org.springframework.stereotype.Service;

@Service
public class GetOrderService {
    final private CounsellorArrangementRepository counsellorArrangementRepository;
    final private SupervisorArrangementRepository supervisorArrangementRepository;

    public GetOrderService(CounsellorArrangementRepository counsellorArrangementRepository, SupervisorArrangementRepository supervisorArrangementRepository) {
        this.counsellorArrangementRepository = counsellorArrangementRepository;
        this.supervisorArrangementRepository = supervisorArrangementRepository;
    }

    /**
     * Get the number of counsellor and supervisor in the given time period
     * @param startTimestamp start timestamp
     * @param endTimestamp   end timestamp
     * @return OrderNum
     */
    public GetAllOrderNumberResponse.OrderNum getOrderNumber(Long startTimestamp, Long endTimestamp) {
        // get the number of counsellor in the given time period
        Long counsellorNum = counsellorArrangementRepository.findCounsellorNumberByTime(startTimestamp, endTimestamp);
        // get the number of supervisor in the given time period
        Long supervisorNum = supervisorArrangementRepository.findSupervisorNumberByTime(startTimestamp, endTimestamp);

        return new GetAllOrderNumberResponse.OrderNum(startTimestamp, endTimestamp, counsellorNum, supervisorNum);
    }
}
