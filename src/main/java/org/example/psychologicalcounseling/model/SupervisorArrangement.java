package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "SupervisorArrangement")
public class SupervisorArrangement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arrangeID")
    private Long arrangeID;

    @Column(name = "supervisorID")
    private Long supervisorID;

    @Column(name = "startTimestamp")
    private Long startTimestamp;

    @Column(name = "endTimestamp")
    private Long endTimestamp;
}
