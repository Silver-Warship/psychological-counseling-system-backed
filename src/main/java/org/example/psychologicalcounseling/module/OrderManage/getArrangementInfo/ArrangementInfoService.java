package org.example.psychologicalcounseling.module.OrderManage.getArrangementInfo;

import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.Supervisor;
import org.example.psychologicalcounseling.repository.CounsellorArrangementRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SupervisorArrangementRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ArrangementInfoService {
    private final CounsellorArrangementRepository counsellorArrangementRepository;
    private final CounsellorRepository counsellorRepository;
    private final SupervisorArrangementRepository supervisorArrangementRepository;
    private final SupervisorRepository supervisorRepository;

    public ArrangementInfoService(CounsellorArrangementRepository counsellorArrangementRepository,
                                  CounsellorRepository counsellorRepository,
                                  SupervisorArrangementRepository supervisorArrangementRepository,
                                  SupervisorRepository supervisorRepository) {
        this.counsellorArrangementRepository = counsellorArrangementRepository;
        this.counsellorRepository = counsellorRepository;
        this.supervisorArrangementRepository = supervisorArrangementRepository;
        this.supervisorRepository = supervisorRepository;
    }

    /**
     * 获取咨询师或监督员的安排信息
     * @param startTimestamp 开始时间戳
     * @param endTimestamp   结束时间戳
     * @param role           角色类型，"counsellor" 或 "supervisor"
     * @return ArrangementInfoResponse 对象，包含咨询师或监督员的安排信息
     */
    public ArrangementInfoResponse getArrangementInfo(Long startTimestamp, Long endTimestamp, String role) {
        ArrangementInfoResponse response = new ArrangementInfoResponse();
        List<ArrangementInfoResponse.Info> infoList = new ArrayList<>();

        if(role.equals("counsellor")){
            // 获取符合条件的咨询师列表
            List<Object[]> cid_arrangeIDList = counsellorArrangementRepository.findIDListByTime(startTimestamp, endTimestamp);
            Counsellor counsellor = new Counsellor();
            for(Object[] cid_arrangeID : cid_arrangeIDList){
                counsellor = counsellorRepository.findByCounsellorID( ((Integer) cid_arrangeID[0]).longValue() );

                infoList.add(new ArrangementInfoResponse.Info(
                        counsellor.getCounsellorID(),
                        ((Integer) cid_arrangeID[1]).longValue() ,
                        counsellor.getNickname(),
                        counsellor.getEmail(),
                        counsellor.getGender().toString(),
                        null,
                        "counsellor"
                ));

            }
        } else if (role.equals("supervisor")) {
            List<Object[]> sid_arrangeIDList = supervisorArrangementRepository.findSupervisorListByTime(startTimestamp, endTimestamp);
            Supervisor supervisor = new Supervisor();
            for(Object[] sid_arrangeID : sid_arrangeIDList){
                supervisor = supervisorRepository.findBySupervisorID(((Integer) sid_arrangeID[0]).longValue());
                infoList.add(new ArrangementInfoResponse.Info(
                        supervisor.getSupervisorID(),
                        ((Integer) sid_arrangeID[1]).longValue() ,
                        supervisor.getNickname(),
                        supervisor.getEmail(),
                        supervisor.getGender().toString(),
                        null,
                        "supervisor"
                ));
            }
        }

        response.setInfo(infoList);
        return response;
    }
}
