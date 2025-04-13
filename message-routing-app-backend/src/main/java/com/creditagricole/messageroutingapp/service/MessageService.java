package com.creditagricole.messageroutingapp.service;

import com.creditagricole.messageroutingapp.dto.MessageDTO;
import com.creditagricole.messageroutingapp.exception.ResourceNotFoundException;
import com.creditagricole.messageroutingapp.mapper.MessageMapper;
import com.creditagricole.messageroutingapp.model.Message;
import com.creditagricole.messageroutingapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Transactional(readOnly = true)
    public Page<MessageDTO> getAllMessages(int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return messageRepository.findAll(pageable)
                .map(messageMapper::toDto);
    }

    @Transactional(readOnly = true)
    public MessageDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id " + id));
        return messageMapper.toDto(message);
    }

}
