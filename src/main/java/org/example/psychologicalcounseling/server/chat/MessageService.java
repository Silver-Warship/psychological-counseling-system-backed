package org.example.psychologicalcounseling.server.chat;

import org.example.psychologicalcounseling.model.Chat.Message;
import org.example.psychologicalcounseling.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<String> getAllMessages() {
        List<Message> records = messageRepository.findAll();
        List<String> messages = new ArrayList<>();
        for (Message record : records) {
            messages.add(record.getContent());
        }

        return messages;
    }



}
