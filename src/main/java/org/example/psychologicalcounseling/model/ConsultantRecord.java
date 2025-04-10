package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ConsultantRecord")
public class ConsultantRecord {
    @Id
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
}
