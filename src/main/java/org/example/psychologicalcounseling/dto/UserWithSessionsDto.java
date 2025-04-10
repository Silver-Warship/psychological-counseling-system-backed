package org.example.psychologicalcounseling.dto;

import org.example.psychologicalcounseling.model.Session;
import org.example.psychologicalcounseling.model.User;

import java.util.List;

public class UserWithSessionsDto {
    private User user;
    private List<Session> sessions;

    // 构造方法
    public UserWithSessionsDto(User user, List<Session> sessions) {
        this.user = user;
        this.sessions = sessions;
    }

    // Getter 和 Setter 方法
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}

