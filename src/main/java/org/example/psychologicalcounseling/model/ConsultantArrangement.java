package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "ConsultantArrangements")
public class ConsultantArrangement {
    @Column(name = "consultantID")
    private Long consultantID;

    @Column(name = "startTimestamp")
    private Long startTimestamp;

    @Column(name = "endTimestamp")
    private Long endTimestamp;
}
