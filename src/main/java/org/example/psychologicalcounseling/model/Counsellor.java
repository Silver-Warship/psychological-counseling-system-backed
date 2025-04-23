package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Counsellor")
public class Counsellor {
    @Id
    @Column(name = "counsellorID")
    private Long counsellorID;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "selfAppraisal")
    private String selfAppraisal;

    public enum Gender {
        male, female, unknown, gunship
    }
}
