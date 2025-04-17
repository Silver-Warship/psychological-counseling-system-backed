package org.example.psychologicalcounseling.module.OrderManage.supervisorOrderManage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.psychologicalcounseling.dto.ResponseBuilder;

@Getter
@Setter
@AllArgsConstructor
public class GetSupervisorOrderResponse extends ResponseBuilder {
    @Data
    static public class TimePeriod {
        private Long startTimestamp;
        private Long endTimestamp;
    }

    public TimePeriod[] timePeriods;
}
