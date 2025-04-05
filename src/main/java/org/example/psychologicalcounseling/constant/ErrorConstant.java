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

     public static final Description sessionAlive = new Description(
            200, "session is alive"
    );

    public static final Description sessionExpired = new Description(
            200, "session expired"
    );

    public static final Description successCreateSession = new Description(
            200, "success create session"
    );

    public static final Description successRegisterConnection = new Description(
            200, "success register connection"
    );

    public static final Description newMessage = new Description(
            200, "there are new message from other user"
    );

    public static final Description newMessageFromGPT = new Description(
            200, "there are new message from GPT"
    );

    public static final Description successAckMessage = new Description(
            200, "success ack message"
    );

    public static final Description sessionClosed = new Description(
            200, "session have been closed"
    );

    public static final Description illegalTimestamp = new Description(
            601, "illegal timestamp, please check your timestamp"
    );

    public static final Description illegalContentType = new Description(
            602, "illegal content type, please check your content type"
    );

    public static final Description invalidRequestType = new Description(
            603, "invalid request type, please check your request type"
    );

    public static final Description sessionNotExist = new Description(
            604, "session not found"
    );

    public static final Description dataContentError = new Description(
            605, "data content error, please check your data content in json"
    );

    public static final Description serverInternalError = new Description(
            606, "server internal error, please contact the administrator"
    );

    public static final Description missParameters = new Description(
            607, "miss parameters, please check your parameters"
    );

    public static final Description missRequestType = new Description(
            608, "miss request type, please check your request type"
    );

    public static final Description missRequestData = new Description(
            609, "miss request data, please check your request data"
    );

    public static final Description missRequestSeq = new Description(
            610, "miss request seq , please check your request sequence"
    );

    public static final Description invalidJson = new Description(
            611, "invalid json, please check your json format"
    );

    public static final Description failCreateGPTSession = new Description(
            612, "fail to create gpt session"
    );

    public static final Description GPTBusy = new Description(
            613, "GPT is busy, please try again later"
    );

    public static final Description negativeMessageID = new Description(
            614, "messageID should be positive, please check your messageID"
    );

    public static final Description noThisUser = new Description(
            615, "no this user, please check your userID"
    );

    public static final Description noThisMessage = new Description(
            616, "no this message, please check your messageID"
    );

    public static final Description noThisSession = new Description(
            617, "no this session, please check your sessionID"
    );

    public static final Description userNotInSession = new Description(
            618, "user not in this session, please check your sessionID and userID"
    );
}
