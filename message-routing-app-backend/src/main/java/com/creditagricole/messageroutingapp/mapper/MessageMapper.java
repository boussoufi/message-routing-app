package com.creditagricole.messageroutingapp.mapper;

import com.creditagricole.messageroutingapp.dto.MessageDTO;
import com.creditagricole.messageroutingapp.model.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MessageMapper {

    public MessageDTO toDto(Message message) {
        if (message == null) {
            return null;
        }

        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setMessageId(message.getMessageId());
        dto.setSource(message.getSource());
        dto.setContent(message.getContent());
        dto.setReceivedDate(message.getReceivedDate());
        dto.setStatus(message.getStatus());
        return dto;
    }

    public List<MessageDTO> toDtoList(List<Message> messages) {
        if (messages == null) {
            return Collections.emptyList();
        }

        List<MessageDTO> dtoList = new ArrayList<>(messages.size());
        for (Message message : messages) {
            dtoList.add(toDto(message));
        }
        return dtoList;
    }
}
