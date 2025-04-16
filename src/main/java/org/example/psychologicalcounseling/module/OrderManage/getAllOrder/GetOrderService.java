package org.example.psychologicalcounseling.module.OrderManage.getAllOrder;

import org.example.psychologicalcounseling.controller.SupervisorArrangementController;
import org.example.psychologicalcounseling.repository.CounsellorArrangementRepository;
import org.example.psychologicalcounseling.repository.SupervisorArrangementRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

@Service
public class GetOrderService {
    private final CounsellorArrangementRepository counsellorArrangementRepository;
    private final SupervisorArrangementRepository supervisorArrangementRepository;

    public GetOrderService(CounsellorArrangementRepository counsellorArrangementRepository, SupervisorArrangementRepository supervisorArrangementRepository) {
        this.counsellorArrangementRepository = counsellorArrangementRepository;
        this.supervisorArrangementRepository = supervisorArrangementRepository;
    }

    public GetAllOrderNumberResponse.OrderNum getOrderNumber(Long startTimestamp, Long endTimestamp) {
        // get the number of counsellor in the given time period
        Long counsellorNum = counsellorArrangementRepository.findCounsellorNumberByTime(startTimestamp, endTimestamp);
        // get the number of supervisor in the given time period
        Long supervisorNum = supervisorArrangementRepository.findSupervisorNumberByTime(startTimestamp, endTimestamp);

        return new GetAllOrderNumberResponse.OrderNum(startTimestamp, endTimestamp, counsellorNum, supervisorNum);
    }
}
