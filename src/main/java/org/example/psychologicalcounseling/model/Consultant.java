package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Consultant")
public class Consultant {
    @Id
    @Column(name = "consultantID")
    private Long consultantID;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "selfAppraisal")
    private Boolean selfAppraisal;

    public enum Gender {
        MALE, FaMALE, UNKNOWN, GUNSHIP
    }
}
