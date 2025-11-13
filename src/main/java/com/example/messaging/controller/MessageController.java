package com.example.messaging.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messaging.model.Message;
import com.example.messaging.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Эндпоинт с проблемой N+1
     * Вызывает метод сервиса, который загружает сообщения и их авторов отдельными запросами
     * Для демонстрации проблемы при профилировании
     */
    @GetMapping("/nplus1")
    public ResponseEntity<List<Message>> getMessagesNPlus1() {
        List<Message> messages = messageService.getMessagesNPlus1();
        return ResponseEntity.ok(messages);
    }

    /**
     * Оптимизированный эндпоинт
     * Использует JOIN FETCH для загрузки сообщений вместе с авторами одним запросом
     * Для сравнения производительности с /nplus1 эндпоинтом
     */
    @GetMapping("/optimized")
    public ResponseEntity<List<Message>> getMessagesOptimized() {
        List<Message> messages = messageService.getMessagesOptimized();
        return ResponseEntity.ok(messages);
    }

    /**
     * Эндпоинт для создания нового сообщения
     * Принимает JSON с полями content и author (с id)
     */
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    }

    /**
     * Эндпоинт для демонстрации LazyInitializationException
     * Возвращает сообщение, но попытка обратиться к lazy-полю author
     * вне транзакции вызовет исключение
     */
    @GetMapping("/{id}/fail")
    public ResponseEntity<Message> getMessageToFail(@PathVariable Long id) {
        Message message = messageService.getMessageToFail(id);
        
        // Попытка обратиться к lazy-полю вне транзакции
        // Это вызовет LazyInitializationException
        String authorName = message.getAuthor().getUsername();
        
        return ResponseEntity.ok(message);
    }

    /**
     * Дополнительный эндпоинт для получения всех сообщений
     */
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /**
     * Эндпоинт для получения сообщения по ID (с оптимизацией)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        return ResponseEntity.ok(message);
    }

    /**
     * Обработчик исключений для данного контроллера
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + ex.getMessage());
    }
}
