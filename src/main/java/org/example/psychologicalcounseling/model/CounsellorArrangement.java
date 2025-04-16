package org.example.psychologicalcounseling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "CounsellorArrangement")
public class CounsellorArrangement {
    @Id
    @Column(name = "arrangeID")
    private Long arrangeID;

    @Column(name = "counsellorID")
    private Long counsellorID;

    @Column(name = "startTimestamp")
    private Long startTimestamp;

    @Column(name = "endTimestamp")
    private Long endTimestamp;
}
