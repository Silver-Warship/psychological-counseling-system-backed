package org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage;

import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.example.psychologicalcounseling.model.Counsellor;
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

    /**
     * 获取 counsellor 的所有排班
     * @param counsellorID counsellor 的 ID
     * @return GetCounsellorOrderResponse 对象，包含 counsellor 的所有排班信息
     */
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

    /**
     * 添加 counsellor 的排班
     * @param counsellors counsellor 的排班列表
     */
    public void addCounsellorOrder(List<UpdateCounsellorOrderRequest.CounsellorOrder> counsellors) {
        // add all orders of the counsellors
        CounsellorArrangement counsellorArrangement= new CounsellorArrangement();;
        for (UpdateCounsellorOrderRequest.CounsellorOrder counsellor : counsellors) {
            counsellorArrangement.setCounsellorID(counsellor.getCounsellorID());
            counsellorArrangement.setStartTimestamp(counsellor.getStartTimestamp());
            counsellorArrangement.setEndTimestamp(counsellor.getEndTimestamp());
            counsellorArrangementRepository.save(counsellorArrangement);
        }

    }

    /**
     * 取消 counsellor 的排班
     * @param arrangeIDs counsellor 的排班 ID 列表
     */
    public void cancelCounsellorOrder(List<Long> arrangeIDs) {
        // delete all orders of counsellors
        for (Long arrangeID : arrangeIDs) {
            counsellorArrangementRepository.deleteById(arrangeID);
        }
    }

    /**
     * 获取 counsellor 的所有排班
     * @param counsellorID counsellor 的 ID
     * @param startTimestamp 排班开始时间
     * @param endTimestamp 排班结束时间
     * @return GetCounsellorOrderResponse 对象，包含 counsellor 的所有排班信息
     */
    public ResponseBuilder getCounsellorOrder(Long counsellorID, Long startTimestamp, Long endTimestamp) {
        // get all orders of the counsellor
        var orders = counsellorArrangementRepository.findAllByCounsellorID(counsellorID, startTimestamp, endTimestamp);
        // create the response
        GetCounsellorOrderResponse.Order[] orderList = new GetCounsellorOrderResponse.Order[orders.size()];
        for (int i = 0; i < orders.size(); i++) {
            orderList[i] = new GetCounsellorOrderResponse.Order(orders.get(i).getStartTimestamp(), orders.get(i).getEndTimestamp());
        }

        return new GetCounsellorOrderResponse(orderList);
    }

    /**
     * 获取 counsellor 的所有排班
     * @param startTimestamp 排班开始时间
     * @param endTimestamp 排班结束时间
     * @return GetCounsellorOrderResponse 对象，包含 counsellor 的所有排班信息
     */
    public ResponseBuilder getCounsellorOrder(Long startTimestamp, Long endTimestamp) {
        // get all orders of the counsellor
        var orders = counsellorArrangementRepository.findAllByCounsellorID(startTimestamp, endTimestamp);
        // create the response
        GetCounsellorOrderResponse.Order[] orderList = new GetCounsellorOrderResponse.Order[orders.size()];
        for (int i = 0; i < orders.size(); i++) {
            orderList[i] = new GetCounsellorOrderResponse.Order(orders.get(i).getStartTimestamp(), orders.get(i).getEndTimestamp());
        }

        return new GetCounsellorOrderResponse(orderList);
    }

    /**
     * 获取在某个时间段内值班的咨询师
     * @param timestamp 时间戳
     * @return 在该时间段内值班的咨询师列表
     */
    public Counsellor[] getOnDutyCounsellor(Long timestamp) {
        var counsellors = counsellorArrangementRepository.findOnDutyCounsellor(timestamp);
        if (counsellors == null) {
            return new Counsellor[0];
        }
        // create the response
        Counsellor[] counsellorList = new Counsellor[counsellors.size()];
        for (int i = 0; i < counsellors.size(); i++) {
            counsellorList[i] = new Counsellor();
            counsellorList[i].setCounsellorID(counsellors.get(i).getCounsellorID());
            counsellorList[i].setNickname(counsellors.get(i).getNickname());
            counsellorList[i].setGender(counsellors.get(i).getGender());
            counsellorList[i].setEmail(counsellors.get(i).getEmail());
            counsellorList[i].setSelfAppraisal(counsellors.get(i).getSelfAppraisal());
        }

        return counsellorList;
    }
}
