package org.example.psychologicalcounseling.constant;

import lombok.Data;

@Data
public class RequestConstant {
    public static final String transmitMessage = "sendMsg";
    public static final String pullUnReceivedMessage = "requestMsg";
    public static final String acknowledgeMessage = "ackMsg";
    public static final String registerConnection = "registerConnection";
    public static final String createSession = "createSession";
    public static final String checkSessionAlive = "checkSessionAlive";
}
