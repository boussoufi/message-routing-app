package com.creditagricole.messageroutingapp.repository;

import com.creditagricole.messageroutingapp.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByOrderByReceivedDateDesc(Pageable pageable);
    Optional<Message> findByMessageId(String messageId);
}
