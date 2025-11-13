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

    @Query("SELECT m FROM Message m JOIN FETCH m.author")
    List<Message> findAllWithAuthors();

    @Query("SELECT m FROM Message m JOIN FETCH m.author WHERE m.id = :id")
    Optional<Message> findByIdWithAuthor(@Param("id") Long id);

    @Query("SELECT m FROM Message m WHERE m.author.id = :authorId")
    List<Message> findByAuthorId(@Param("authorId") Long authorId);
}
