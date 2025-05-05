package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "userID", nullable = false, length = 11)
    private Long uid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        male, female, unknown, gunship;
    }
}

