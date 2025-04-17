package org.example.psychologicalcounseling.module.OrderManage.supervisorOrderManage;

import lombok.Data;

@Data
public class AddSupervisorOrderDto {
    private Long supervisorID;
    private Long startTimestamp;
    private Long endTimestamp;
}
