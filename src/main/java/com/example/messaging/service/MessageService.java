package com.example.messaging.service;

import com.example.messaging.model.Message;
import com.example.messaging.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // TODO: Метод с проблемой N+1
    @Transactional(readOnly = true)
    public List<Message> getMessagesNPlus1() {
        return null;
    }

    // TODO: Оптимизированный метод
    @Transactional(readOnly = true)
    public List<Message> getMessagesOptimized() {
        return null;
    }

    // TODO: Метод для демонстрации LazyInitializationException
    @Transactional(readOnly = true)
    public Message getMessageToFail(Long id) {
        return null;
    }

    // TODO: Метод для создания сообщений
    @Transactional
    public Message createMessage(Message message) {
        return null;
    }
}
