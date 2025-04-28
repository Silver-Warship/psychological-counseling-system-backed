package org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage;


import org.example.psychologicalcounseling.model.CounsellorArrangement;
import org.example.psychologicalcounseling.repository.CounsellorArrangementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void addCounsellorOrder(List<UpdateCounsellorOrderRequest.CounsellorOrder> counsellors) {
        // add all orders of the counsellors

        CounsellorArrangement counsellorArrangement= new CounsellorArrangement();;
        for (UpdateCounsellorOrderRequest.CounsellorOrder counsellor : counsellors) {
            counsellorArrangement.setCounsellorID(counsellor.getCounsellorID());
            //System.out.println(counsellor.getCounsellorID());
            counsellorArrangement.setStartTimestamp(counsellor.getStartTimestamp());
            counsellorArrangement.setEndTimestamp(counsellor.getEndTimestamp());
            counsellorArrangementRepository.save(counsellorArrangement);
        }

    }

    public void cancelCounsellorOrder(List<Long> arrangeIDs) {
        // delete all orders of counsellors
        for (Long arrangeID : arrangeIDs) {
            counsellorArrangementRepository.deleteById(arrangeID);
        }
    }
}
