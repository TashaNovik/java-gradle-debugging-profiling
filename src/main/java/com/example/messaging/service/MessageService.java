package com.example.messaging.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.messaging.model.Message;
import com.example.messaging.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessagesNPlus1() {
        List<Message> messages = messageRepository.findAll();
        messages.forEach(message -> {
            String authorName = message.getAuthor().getUsername();
        });
        return messages;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessagesOptimized() {
        return messageRepository.findAllWithAuthors();
    }

    @Transactional(readOnly = true)
    public Message getMessageToFail(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
        return message;
    }

    public Message getMessageToFailNoTransaction(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
        return message;
    }

    @Transactional
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Message getMessageById(Long id) {
        return messageRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
    }
}
