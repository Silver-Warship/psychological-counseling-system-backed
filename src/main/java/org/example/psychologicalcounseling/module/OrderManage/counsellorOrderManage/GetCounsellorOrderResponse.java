package org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.psychologicalcounseling.dto.ResponseBuilder;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCounsellorOrderResponse extends ResponseBuilder {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Order {
        Long startTimestamp;
        Long endTimestamp;
    }

    public Order[] orders;
}
