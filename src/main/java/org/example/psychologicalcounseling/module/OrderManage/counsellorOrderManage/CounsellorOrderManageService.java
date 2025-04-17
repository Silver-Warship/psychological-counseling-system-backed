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

    public void addCounsellorOrder(List<UpdateCounsellorOrderRequest.CounsellorOrder> conunsellors) {
        // add all orders of the counsellors

        CounsellorArrangement counsellorArrangement= new CounsellorArrangement();;
        for (UpdateCounsellorOrderRequest.CounsellorOrder conunsellor : conunsellors) {
            counsellorArrangement.setCounsellorID(conunsellor.getCounsellorID());
            //System.out.println(conunsellor.getCounsellorID());
            counsellorArrangement.setStartTimestamp(conunsellor.getStartTimestamp());
            counsellorArrangement.setEndTimestamp(conunsellor.getEndTimestamp());
            counsellorArrangementRepository.save(counsellorArrangement);
        }

    }

    public void cancelCounsellorOrder(List<UpdateCounsellorOrderRequest.CounsellorOrder> conunsellors) {
        // delete all orders of counsellors
        CounsellorArrangement counsellorArrangement= new CounsellorArrangement();
        for (UpdateCounsellorOrderRequest.CounsellorOrder conunsellor : conunsellors) {
            counsellorArrangement=counsellorArrangementRepository.findArrangementByParams(
                    conunsellor.getCounsellorID(),
                    conunsellor.getStartTimestamp(),
                    conunsellor.getEndTimestamp());
            counsellorArrangementRepository.delete(counsellorArrangement);
        }
    }
}
