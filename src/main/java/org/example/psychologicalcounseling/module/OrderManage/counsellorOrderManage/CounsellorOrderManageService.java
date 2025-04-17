package org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage;

import org.example.psychologicalcounseling.repository.CounsellorArrangementRepository;
import org.springframework.stereotype.Service;

@Service
public class CounsellorOrderManageService {
    private final CounsellorArrangementRepository counsellorArrangementRepository;

    public CounsellorOrderManageService(CounsellorArrangementRepository counsellorArrangementRepository) {
        this.counsellorArrangementRepository = counsellorArrangementRepository;
    }

    public GetCounsellorOrderResponse getCounsellorOrder(Long counsellorID) {
        // get all orders of the counsellor
        var orders = counsellorArrangementRepository.findAllByCounsellorID(counsellorID);
        // create the response
        GetCounsellorOrderResponse.Order[] orderList = new GetCounsellorOrderResponse.Order[orders.size()];
        for (int i = 0; i < orders.size(); i++) {
            Long startTimestamp = orders.get(i).getStartTimestamp();
            Long endTimestamp = orders.get(i).getEndTimestamp();
            orderList[i] = new GetCounsellorOrderResponse.Order(startTimestamp, endTimestamp);
        }

        return new GetCounsellorOrderResponse(orderList);
    }
}
