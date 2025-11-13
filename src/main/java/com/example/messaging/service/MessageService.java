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

    /**
     * Метод с проблемой N+1
     * Загружает все сообщения без eager-загрузки авторов,
     * что приводит к дополнительным запросам при обращении к author
     */
    @Transactional(readOnly = true)
    public List<Message> getMessagesNPlus1() {
        // Один запрос для загрузки всех сообщений
        List<Message> messages = messageRepository.findAll();
        
        // N дополнительных запросов - по одному для каждого автора
        // Это происходит при обращении к полю author в контроллере или сервисе
        messages.forEach(message -> {
            // Принудительная загрузка автора (для демонстрации N+1)
            String authorName = message.getAuthor().getUsername();
        });
        
        return messages;
    }

    /**
     * Оптимизированный метод
     * Использует JOIN FETCH для загрузки сообщений вместе с авторами одним запросом
     */
    @Transactional(readOnly = true)
    public List<Message> getMessagesOptimized() {
        // Один запрос с JOIN для загрузки сообщений и авторов
        return messageRepository.findAllWithAuthors();
    }

    /**
     * Метод для демонстрации LazyInitializationException
     * Возвращает сообщение, но транзакция закрывается после выхода из метода
     */
    @Transactional(readOnly = true)
    public Message getMessageToFail(Long id) {
        // Загружаем сообщение в рамках транзакции
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
        
        // После выхода из метода транзакция закрывается
        // Попытка обратиться к lazy-полю author вне транзакции вызовет LazyInitializationException
        return message;
    }

    /**
     * Метод без транзакции для гарантированной демонстрации LazyInitializationException
     */
    public Message getMessageToFailNoTransaction(Long id) {
        // Без @Transactional - контекст персистентности отсутствует
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
        
        // Попытка обратиться к lazy-полю вызовет LazyInitializationException
        return message;
    }

    /**
     * Метод для создания нового сообщения
     */
    @Transactional
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Метод для получения всех сообщений (простой)
     */
    @Transactional(readOnly = true)
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Метод для получения сообщения по ID с загруженным автором
     */
    @Transactional(readOnly = true)
    public Message getMessageById(Long id) {
        return messageRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
    }
}
