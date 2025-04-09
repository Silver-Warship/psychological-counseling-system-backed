package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "CounsellorArrangements")
public class CounsellorArrangement {
    @Column(name = "counsellorID")
    private Long counsellorID;

    @Column(name = "startTimestamp")
    private Long startTimestamp;

    @Column(name = "endTimestamp")
    private Long endTimestamp;
}
