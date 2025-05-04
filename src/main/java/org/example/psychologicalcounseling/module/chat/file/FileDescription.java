package org.example.psychologicalcounseling.module.chat.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.psychologicalcounseling.model.Message;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDescription {
    private Long senderID;
    private Long receiverID;
    private String fileData; // base64 encoded string
    private String FileSuffix;
}
