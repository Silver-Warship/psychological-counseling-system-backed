package org.example.psychologicalcounseling.module.getAll;

import lombok.Data;
import org.example.psychologicalcounseling.dto.ResponseBuilder;

import java.util.List;


@Data
public class GetAllResponse extends ResponseBuilder {

    @Data
    static class Info{
        private Long id;
        private String nickname;
        private String email;

        public Info(Long counsellorID, String nickname, String email) {
            this.id = counsellorID;
            this.nickname = nickname;
            this.email = email;
        }
    }

    List<Info> Infos;
}
