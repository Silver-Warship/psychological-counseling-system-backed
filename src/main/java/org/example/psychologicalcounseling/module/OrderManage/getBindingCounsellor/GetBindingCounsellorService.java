package org.example.psychologicalcounseling.module.OrderManage.getBindingCounsellor;

import org.example.psychologicalcounseling.repository.SupervisorManageRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.springframework.stereotype.Service;

@Service
public class GetBindingCounsellorService {
    private final SupervisorManageRepository supervisorManageRepository;
    private final CounsellorRepository counsellorRepository;

    public GetBindingCounsellorService(SupervisorManageRepository supervisorManageRepository, CounsellorRepository counsellorRepository) {
        this.supervisorManageRepository = supervisorManageRepository;
        this.counsellorRepository = counsellorRepository;
    }

    /**
     * Get the list of counsellors bound to the admin
     * @param supervisorID The ID of the admin
     * @return A response containing the list of counsellors bound to the admin
     */
    public GetBindingCounsellorResponse getBindingCounsellor(Long supervisorID) {
        // get counsellor IDs bound to the admin
        var counsellorIds = supervisorManageRepository.findCounsellorIDBySupervisorID(supervisorID);
        // get counsellor names
        var counsellorNames = counsellorIds.stream().map(counsellorRepository::findCounsellorNameByCounsellorID).toList();
        // create the response
        GetBindingCounsellorResponse.CounsellorInfo[] counsellors = new GetBindingCounsellorResponse.CounsellorInfo[counsellorIds.size()];
        for (int i = 0; i < counsellorIds.size(); i++) {
            GetBindingCounsellorResponse.CounsellorInfo counsellorInfo = new GetBindingCounsellorResponse.CounsellorInfo();
            counsellorInfo.setCounsellorId(counsellorIds.get(i));
            counsellorInfo.setCounsellorName(counsellorNames.get(i));
            counsellors[i] = counsellorInfo;
        }
        GetBindingCounsellorResponse response = new GetBindingCounsellorResponse();
        response.setCounsellors(counsellors);

        return response;
    }
}
