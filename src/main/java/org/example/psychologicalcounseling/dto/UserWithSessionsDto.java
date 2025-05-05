package org.example.psychologicalcounseling.dto;

import lombok.Data;
import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.model.User;

import java.util.List;

@Data
public class UserWithSessionsDto {
    private User user;
    private List<Session> sessions;

    // 构造方法
    public UserWithSessionsDto(User user, List<Session> sessions) {
        this.user = user;
        this.sessions = sessions;
    }
}

