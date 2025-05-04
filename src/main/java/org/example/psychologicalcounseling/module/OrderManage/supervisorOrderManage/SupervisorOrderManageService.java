package org.example.psychologicalcounseling.module.OrderManage.supervisorOrderManage;

import org.example.psychologicalcounseling.model.SupervisorArrangement;
import org.example.psychologicalcounseling.repository.SupervisorArrangementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupervisorOrderManageService {
    final private SupervisorArrangementRepository supervisorArrangementRepository;

    public SupervisorOrderManageService(SupervisorArrangementRepository supervisorArrangementRepository) {
        this.supervisorArrangementRepository = supervisorArrangementRepository;
    }

    private GetSupervisorOrderResponse _supervisorOrderAdapt(List<SupervisorArrangement> arrangements) {
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

    public GetSupervisorOrderResponse getSupervisorOrder(Long supervisorID, Long startTimestamp, Long endTimestamp) {
        // get all orders of the supervisor
        var arrangements = supervisorArrangementRepository.findBySupervisorIDAndStartTimestampBetween(supervisorID, startTimestamp, endTimestamp);

        // create the response
        return _supervisorOrderAdapt(arrangements);
    }

    public GetSupervisorOrderResponse getAllSupervisorOrder(Long startTimestamp, Long endTimestamp) {
        // get all orders of the supervisor
        var arrangements = supervisorArrangementRepository.findByStartTimestampBetween(startTimestamp, endTimestamp);

        // create the response
        return _supervisorOrderAdapt(arrangements);
    }

    public boolean addSupervisorOrder(Long supervisorID, Long startTimestamp, Long endTimestamp) {
        try {
            // construct new order
            SupervisorArrangement newArrangement = new SupervisorArrangement();
            newArrangement.setSupervisorID(supervisorID);
            newArrangement.setStartTimestamp(startTimestamp);
            newArrangement.setEndTimestamp(endTimestamp);
            // save the new order
            supervisorArrangementRepository.save(newArrangement);
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    public boolean removeSupervisorOrder(Long supervisorID) {
        try {
            supervisorArrangementRepository.deleteById(supervisorID);
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }
}
