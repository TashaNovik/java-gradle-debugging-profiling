package com.example.messaging.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.messaging.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Оптимизированный метод для загрузки всех сообщений вместе с авторами
     * Использует JOIN FETCH для предотвращения проблемы N+1
     */
    @Query("SELECT m FROM Message m JOIN FETCH m.author")
    List<Message> findAllWithAuthors();

    /**
     * Оптимизированный метод для загрузки одного сообщения с автором
     * Использует JOIN FETCH для загрузки автора в одном запросе
     */
    @Query("SELECT m FROM Message m JOIN FETCH m.author WHERE m.id = :id")
    Optional<Message> findByIdWithAuthor(@Param("id") Long id);

    /**
     * Метод для поиска сообщений по автору
     */
    @Query("SELECT m FROM Message m WHERE m.author.id = :authorId")
    List<Message> findByAuthorId(@Param("authorId") Long authorId);
}
