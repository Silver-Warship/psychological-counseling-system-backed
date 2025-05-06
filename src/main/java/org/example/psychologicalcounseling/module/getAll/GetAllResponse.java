package org.example.psychologicalcounseling.module.getAll;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.psychologicalcounseling.dto.ResponseBuilder;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetAllResponse extends ResponseBuilder {
    @Data
    static class Info{
        private Long id;
        private String nickname;
        private String email;
        private String gender;

        public Info(Long counsellorID, String nickname, String email) {
            this.id = counsellorID;
            this.nickname = nickname;
            this.email = email;
        }

        public Info(Long counsellorID, String nickname, String email, String gender) {
            this.id = counsellorID;
            this.nickname = nickname;
            this.email = email;
            this.gender = gender;
        }
    }

    List<Info> Infos;
}
