package org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage;

import org.example.psychologicalcounseling.repository.SupervisorArrangementRepository;
import org.springframework.stereotype.Service;

@Service
public class SupervisorOrderManageService {
    final private SupervisorArrangementRepository supervisorArrangementRepository;

    public SupervisorOrderManageService(SupervisorArrangementRepository supervisorArrangementRepository) {
        this.supervisorArrangementRepository = supervisorArrangementRepository;
    }

    public GetSupervisorOrderResponse getSupervisorOrder(Long supervisorID, Long startTimestamp, Long endTimestamp) {
        // get all orders of the supervisor
        var arrangements = supervisorArrangementRepository.findBySupervisorIDAndStartTimestampBetween(supervisorID, startTimestamp, endTimestamp);

        // create the response
        GetSupervisorOrderResponse.TimePeriod[] timePeriods = new GetSupervisorOrderResponse.TimePeriod[arrangements.size()];
        for (int i = 0; i < arrangements.size(); i++) {
            Long start = arrangements.get(i).getStartTimestamp();
            Long end = arrangements.get(i).getEndTimestamp();
            timePeriods[i] = new GetSupervisorOrderResponse.TimePeriod();
            timePeriods[i].setStartTimestamp(start);
            timePeriods[i].setEndTimestamp(end);
        }

        return new GetSupervisorOrderResponse(timePeriods);
    }
}
