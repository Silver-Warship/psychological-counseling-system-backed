package org.example.psychologicalcounseling.module.getAll;

import org.example.psychologicalcounseling.model.Counsellor;
import org.example.psychologicalcounseling.model.Supervisor;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.example.psychologicalcounseling.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllService {

    @Autowired
    CounsellorRepository counsellorRepository;
    @Autowired
    SupervisorRepository supervisorRepository;

    public GetAllResponse getAll(String role) {
        GetAllResponse getAllResponse = new GetAllResponse();
        List <GetAllResponse.Info> infoList = new ArrayList<>();

        if(role.equals("counsellor")){
            // 假设从数据库中查询 counsellor 的数据
            List<Counsellor> counsellors = counsellorRepository.findAll();
            for (Counsellor counsellor : counsellors) {
                infoList.add(
                        new GetAllResponse.Info(
                                counsellor.getCounsellorID(),
                                counsellor.getNickname(),
                                counsellor.getEmail()
                        )
                );
            }
        }else if (role.equals("supervisor")){
            // 假设从数据库中查询 supervisor 的数据
            List<Supervisor> supervisors = supervisorRepository.findAll();
            for (Supervisor supervisor : supervisors) {
                infoList.add(
                        new GetAllResponse.Info(
                                supervisor.getSupervisorID(),
                                supervisor.getNickname(),
                                supervisor.getEmail()
                        )
                );
            }
        }else {
            getAllResponse.setCode(500);
            getAllResponse.setCodeMsg("Bad Request : Role Error");
        }

        getAllResponse.setInfos(infoList);
        return getAllResponse;
    }

}
