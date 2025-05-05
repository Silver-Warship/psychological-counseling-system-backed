package org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage;

import lombok.Data;
import java.util.List;

@Data
public class UpdateCounsellorOrderRequest {
    private List<CounsellorOrder> counsellors;

    @Data
    public static class CounsellorOrder {
        private Long counsellorID;
        private Long startTimestamp;
        private Long endTimestamp;
    }
}
