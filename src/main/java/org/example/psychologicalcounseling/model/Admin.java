package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Consultant.Gender gender;

    public enum Gender {
        MALE, FaMALE, UNKNOWN, GUNSHIP
    }
}
