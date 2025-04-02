package org.example.psychologicalcounseling.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ErrorConstant {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Description {
        public int code;
        public String codeMsg;
    }

    public static final Description successSendMessage = new Description(
            200, "success send message"
    );

    public static final Description successPullMessage = new Description(
            200, "success pull message"
    );

    public static final Description illegalTimestamp = new Description(
            601, "illegal timestamp, please check your timestamp"
    );

    public static final Description illegalContentType = new Description(
            602, "illegal content type, please check your content type"
    );

    public static final Description sessionAlive = new Description(
            200, "session is alive"
    );

    public static final Description sessionNotExist = new Description(
            604, "session not found"
    );

    public static final Description sessionExpired = new Description(
            200, "session expired"
    );

    public static final Description successRegisterConnection = new Description(
            200, "success register connection"
    );

    public static final Description newMessage = new Description(
            200, "there are new message from other user"
    );

    public static final Description successAckMessage = new Description(
            200, "success ack message"
    );
}
