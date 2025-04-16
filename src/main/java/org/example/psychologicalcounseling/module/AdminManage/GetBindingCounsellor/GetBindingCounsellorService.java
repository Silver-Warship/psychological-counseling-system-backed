package org.example.psychologicalcounseling.module.AdminManage.GetBindingCounsellor;

import org.example.psychologicalcounseling.repository.AdminManageRepository;
import org.example.psychologicalcounseling.repository.CounsellorRepository;
import org.springframework.stereotype.Service;

@Service
public class GetBindingCounsellorService {
    private final AdminManageRepository adminManageRepository;
    private final CounsellorRepository counsellorRepository;

    public GetBindingCounsellorService(AdminManageRepository adminManageRepository, CounsellorRepository counsellorRepository) {
        this.adminManageRepository = adminManageRepository;
        this.counsellorRepository = counsellorRepository;
    }

    public GetBindingCounsellorResponse getBindingCounsellor(Long adminId) {
        // get counsellor IDs bound to the admin
        var counsellorIds = adminManageRepository.findCounsellorIDByAdminID(adminId);
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
