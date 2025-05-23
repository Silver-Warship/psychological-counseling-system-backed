package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.constant.RequestConstant;
import org.example.psychologicalcounseling.controller.MessageController;
import org.example.psychologicalcounseling.module.chat.session.checkSessionAlive.CheckSessionAliveResponse;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController extends MessageController {
    public ChatController() {
        super();
        registerRequest(RequestConstant.transmitMessage, TransmitMessageController.class);
        registerRequest(RequestConstant.pullUnReceivedMessage, PullUnReceiveMessageController.class);
        registerRequest(RequestConstant.acknowledgeMessage, AcknowledgeMessageController.class);
        registerRequest(RequestConstant.createSession, CreateSessionController.class);
        registerRequest(RequestConstant.checkSessionAlive, CheckSessionAliveController.class);
        registerRequest(RequestConstant.registerConnection, RegisterConnectionController.class);
        registerRequest(RequestConstant.chatWithGPT, ChatWithGPTController.class);
        registerRequest(RequestConstant.closeSession, CloseSessionController.class);
        registerRequest(RequestConstant.transmitFile, TransmitFileController.class);
    }
}
