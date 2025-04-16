package org.example.psychologicalcounseling.module.OrderManage.getAllOrder;

import lombok.*;
import org.example.psychologicalcounseling.dto.ResponseBuilder;

@Setter
@Getter
public class GetAllOrderNumberResponse extends ResponseBuilder {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderNum {
        Long startTimestamp;
        Long endTimestamp;
        Long counsellorNumber;
        Long supervisorNumber;
    }

    public OrderNum[] orderList;
}
