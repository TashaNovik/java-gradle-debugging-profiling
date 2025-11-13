package com.example.messaging.controller;

import com.example.messaging.model.Message;
import com.example.messaging.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // TODO: Эндпоинт с проблемой N+1
    @GetMapping("/nplus1")
    public ResponseEntity<List<Message>> getMessagesNPlus1() {
        return null;
    }

    // TODO: Оптимизированный эндпоинт
    @GetMapping("/optimized")
    public ResponseEntity<List<Message>> getMessagesOptimized() {
        return null;
    }

    // TODO: Эндпоинт для создания сообщения
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return null;
    }

    // TODO: Эндпоинт для демонстрации LazyInitializationException
    @GetMapping("/{id}/fail")
    public ResponseEntity<Message> getMessageToFail(@PathVariable Long id) {
        return null;
    }
}
