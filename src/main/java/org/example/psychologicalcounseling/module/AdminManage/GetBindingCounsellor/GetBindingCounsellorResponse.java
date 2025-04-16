package org.example.psychologicalcounseling.module.AdminManage.GetBindingCounsellor;

import lombok.Data;
import lombok.Setter;
import org.example.psychologicalcounseling.dto.ResponseBuilder;

@Setter
public class GetBindingCounsellorResponse extends ResponseBuilder {
    @Data
    static public class CounsellorInfo {
        public Long counsellorId;
        public String counsellorName;
    }

    public CounsellorInfo[] counsellors;
}
