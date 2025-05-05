package org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage;

import lombok.Data;
import java.util.List;

@Data
public class CancelCounsellorOrderRequest {
    List<Long> arrangeIDs;
}
