package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Admin")
public class Admin {
    @Id
    @Column(name = "adminID")
    private Long adminID;
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        male, female, unknown, gunship
    }
}
