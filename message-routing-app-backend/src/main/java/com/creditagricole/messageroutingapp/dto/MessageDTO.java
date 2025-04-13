package com.creditagricole.messageroutingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private String messageId;
    private String source;
    private String content;
    private LocalDateTime receivedDate;
    private String status;
}
