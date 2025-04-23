package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Supervisor")
public class Supervisor {
    @Id
    @Column(name = "supervisorID")
    private Long supervisorID;
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
