package com.example.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.messaging.model.Author;
import com.example.messaging.model.Message;
import com.example.messaging.repository.AuthorRepository;
import com.example.messaging.repository.MessageRepository;
import com.example.messaging.service.MessageService;

@SpringBootTest
@Transactional
class MessagingApplicationTests {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
        authorRepository.deleteAll();

        author1 = new Author("John Doe");
        author2 = new Author("Jane Smith");
        authorRepository.save(author1);
        authorRepository.save(author2);

        Message message1 = new Message("Test message 1", author1);
        Message message2 = new Message("Test message 2", author1);
        Message message3 = new Message("Test message 3", author2);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
    }

    @Test
    void contextLoads() {
        assertThat(messageService).isNotNull();
        assertThat(messageRepository).isNotNull();
        assertThat(authorRepository).isNotNull();
    }

    @Test
    void testGetMessagesNPlus1() {
        List<Message> messages = messageService.getMessagesNPlus1();
        assertThat(messages).isNotEmpty();
        assertThat(messages).hasSize(3);
    }

    @Test
    void testGetMessagesOptimized() {
        List<Message> messages = messageService.getMessagesOptimized();
        assertThat(messages).isNotEmpty();
        assertThat(messages).hasSize(3);
        assertThat(messages.get(0).getAuthor()).isNotNull();
    }

    @Test
    void testCreateMessage() {
        Message newMessage = new Message("New test message", author1);
        Message savedMessage = messageService.createMessage(newMessage);
        
        assertThat(savedMessage).isNotNull();
        assertThat(savedMessage.getId()).isNotNull();
        assertThat(savedMessage.getContent()).isEqualTo("New test message");
        assertThat(savedMessage.getAuthor()).isEqualTo(author1);
    }

    @Test
    void testGetAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        assertThat(messages).hasSize(3);
    }

    @Test
    void testGetMessageById() {
        Message message = messageRepository.findAll().get(0);
        Message foundMessage = messageService.getMessageById(message.getId());
        
        assertThat(foundMessage).isNotNull();
        assertThat(foundMessage.getId()).isEqualTo(message.getId());
        assertThat(foundMessage.getAuthor()).isNotNull();
    }

    @Test
    void testGetMessageToFail() {
        Message message = messageRepository.findAll().get(0);
        Message foundMessage = messageService.getMessageToFail(message.getId());
        
        assertThat(foundMessage).isNotNull();
        assertThat(foundMessage.getId()).isEqualTo(message.getId());
    }

    @Test
    void testFindAllWithAuthors() {
        List<Message> messages = messageRepository.findAllWithAuthors();
        assertThat(messages).hasSize(3);
        messages.forEach(message -> {
            assertThat(message.getAuthor()).isNotNull();
            assertThat(message.getAuthor().getUsername()).isNotBlank();
        });
    }

    @Test
    void testFindByIdWithAuthor() {
        Message message = messageRepository.findAll().get(0);
        Message foundMessage = messageRepository.findByIdWithAuthor(message.getId()).orElse(null);
        
        assertThat(foundMessage).isNotNull();
        assertThat(foundMessage.getAuthor()).isNotNull();
    }

    @Test
    void testFindByAuthorId() {
        List<Message> messagesOfAuthor1 = messageRepository.findByAuthorId(author1.getId());
        assertThat(messagesOfAuthor1).hasSize(2);
        
        List<Message> messagesOfAuthor2 = messageRepository.findByAuthorId(author2.getId());
        assertThat(messagesOfAuthor2).hasSize(1);
    }

    @Test
    void testGetMessageByIdNotFound() {
        assertThatThrownBy(() -> messageService.getMessageById(999L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Message not found with id: 999");
    }

    @Test
    void testAuthorToString() {
        String authorString = author1.toString();
        assertThat(authorString).contains("Author");
        assertThat(authorString).contains("John Doe");
    }

    @Test
    void testMessageToString() {
        Message message = new Message("Test content", author1);
        String messageString = message.toString();
        assertThat(messageString).contains("Message");
        assertThat(messageString).contains("Test content");
    }
}
