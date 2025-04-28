package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ConsultantRecord")
public class ConsultantRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordID")
    Long recordID;

    @Column(name = "counsellorID")
    Long counsellorID;

    @Column(name = "userID")
    Long userID;

    @Column(name = "sessionID")
    Long sessionID;

    @Column(name = "userRating")
    Float userRating;

    @Column(name = "appraisal")
    String appraisal;

    @Column(name = "counsellorAppraisal")
    String counsellorAppraisal;
}
