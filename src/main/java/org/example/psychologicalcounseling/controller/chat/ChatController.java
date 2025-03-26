package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.controller.MessageController;
import org.example.psychologicalcounseling.dto.chat.CheckSessionAliveResponse;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController extends MessageController {
    public ChatController() {
        super();
        registerRequest("sendMsg", TransmitMessageController.class);
        registerRequest("requestMsg", PullUnReceiveMessageController.class);
        registerRequest("ackMsg", AcknowledgeMessageController.class);
        registerRequest("createSession", CreateSessionController.class);
        registerRequest("checkSessionAlive", CheckSessionAliveResponse.class);
    }
}
