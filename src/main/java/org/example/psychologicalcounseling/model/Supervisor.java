package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Counsellor.Gender gender;

    public enum Gender {
        MALE, FaMALE, UNKNOWN, GUNSHIP
    }
}
