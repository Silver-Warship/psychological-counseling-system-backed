package org.example.psychologicalcounseling.module.OrderManage.getArrangementInfo;

import lombok.Data;
import org.example.psychologicalcounseling.dto.ResponseBuilder;

import java.util.List;

@Data
public class ArrangementInfoResponse extends ResponseBuilder{
    private List<Info> info;

    @Data
    public static class Info {
        private Long id;
        private Long arrangeID;
        private String nickname;
        private String email;
        private String gender;
        private String avater;
        private String role;

        public Info(Long id, Long arrangeID, String nickname, String email, String gender, String avater, String role) {
            this.id = id;
            this.arrangeID = arrangeID;
            this.nickname = nickname;
            this.email = email;
            this.gender = gender;
            this.avater = avater;
            this.role = role;
        }
    }


}
