package org.example.psychologicalcounseling.controller.chat;

import org.example.psychologicalcounseling.controller.MessageController;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController extends MessageController {
    public ChatController() {
        super();
        registerRequest("transmitMessage", TransmitMessageController.class);
        registerRequest("pullUnReceivedMessage", PullUnReceiveMessageController.class);
        registerRequest("acknowledgeMessage", AcknowledgeMessageController.class);
    }
}
