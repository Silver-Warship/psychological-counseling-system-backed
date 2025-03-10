package org.example.psychologicalcounseling.model.Chat;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "UserMessage")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid", nullable = false, length = 20)
    private Long mid;

    @Column(nullable = false)
    private Long firstUid;

    @Column(nullable = false)
    private Long secondUid;

    @Column(nullable = false)
    private int isFirstToSecond;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long timeStamps;
}
