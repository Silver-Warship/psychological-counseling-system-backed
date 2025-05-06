package org.example.psychologicalcounseling.module.info;

import lombok.Data;
import org.example.psychologicalcounseling.model.User;

@Data
public class EditRequestDto {
    private Long uid;
    private String gender;
    private String password;
    private String nickname;
}
