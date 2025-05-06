package org.example.psychologicalcounseling.module.OrderManage.counsellorOrderManage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.psychologicalcounseling.dto.ResponseBuilder;
import org.example.psychologicalcounseling.model.Counsellor;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetOnDutyCounsellorResponse extends ResponseBuilder {
    public Counsellor[] counsellors;
}
