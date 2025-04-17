package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CounsellorArrangement")
public class CounsellorArrangement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arrangeID")
    private Long arrangeID;

    @Column(name = "counsellorID")
    private Long counsellorID;

    @Column(name = "startTimestamp")
    private Long startTimestamp;

    @Column(name = "endTimestamp")
    private Long endTimestamp;
}
