package org.example.psychologicalcounseling.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageID", nullable = false, length = 11)
    private Long messageID;

    @Column(name = "sessionID", length = 11)
    private Long sessionID;

    // contentType includes : text, image, file, largeFile, record
    @Enumerated(EnumType.STRING)
    @Column(name = "contentType", nullable = false)
    private FileType contentType;

    // content is the actual message content
    @Column(name = "content", nullable = false)
    private String content;

    // senderID and receiverID are the user IDs of the sender and receiver
    @Column(name = "senderID", length = 11)
    private Long senderID;
    @Column(name = "receiverID", length = 11)
    private Long receiverID;

    // timestamp is the time when the message is sent
    @Column(name = "sendTimestamp", nullable = false)
    private long sendTimestamp;

    // receiveTimestamp is the time when the message is received
    private long receiveTimestamp;

    // this field is used to indicate the status of the message
    // 0 unread, 1 read, 2 deleted
    @Column(name = "status", nullable = false)
    private int status;

    public enum FileType {
        TEXT, IMAGE, FILE, LARGE_FILE, RECORD
    }
}
