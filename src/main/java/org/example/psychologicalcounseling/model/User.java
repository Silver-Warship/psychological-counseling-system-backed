package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false, length = 20)
    private Long uid;

    @Column(nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    public enum Gender {
        male, female, unknown, gunship
    }

    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN, USER
    }
}

