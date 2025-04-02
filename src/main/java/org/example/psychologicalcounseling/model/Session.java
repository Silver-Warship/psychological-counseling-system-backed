package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "Session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessionID", nullable = false, length = 20)
    private Long sessionID;

    // start timestamp and end timestamp of the session
    private Long startTimestamp;
    private Long endTimestamp;

    // flag to indicate if the session is closed
    private Boolean isClosed;

    // the time of the last message sent in the session
    private Long lastMessageTimestamp;

    // the id of the initiator of the session
    @Column(nullable = false, length = 20)
    private Long firstUserID;
    @Column(nullable = false, length = 20)
    private Long SecondUserID;
}
