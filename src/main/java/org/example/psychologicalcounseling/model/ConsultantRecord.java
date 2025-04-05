package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "ConsultantRecord")
public class ConsultantRecord {
    @Column(name = "consultantID")
    Long consultantID;

    @Column(name = "userID")
    Long userID;

    @Column(name = "sessionID")
    Long sessionID;

    @Column(name = "userRating")
    Float userRating;

    @Column(name = "appraisal")
    String appraisal;
}
