package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid", nullable = false, length = 20)
    private Long mID;
    private Long sessionID;

    // contentType includes : text, image, file, largeFile, record
    @Enumerated(EnumType.STRING)
    private FileType contentType;

    // content is the actual message content
    private String content;

    // senderID and receiverID are the user IDs of the sender and receiver
    private Long senderID;
    private Long receiverID;

    // timestamp is the time when the message is sent
    private long sendTimestamp;

    // receiveTimestamp is the time when the message is received
    private long receiveTimestamp;

    // this field is used to indicate the status of the message
    // 0 unread, 1 read, 2 deleted
    private int status;

    public enum FileType {
        TEXT, IMAGE, FILE, LARGE_FILE, RECORD
    }
}
