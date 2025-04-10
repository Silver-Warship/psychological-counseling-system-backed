package org.example.psychologicalcounseling.module.user.info;

import lombok.Data;
import org.example.psychologicalcounseling.model.User;

@Data
public class EditRequestDto {
    private Long uid;
    private User.Gender gender;
    private String password;
    private String nickname;
}
