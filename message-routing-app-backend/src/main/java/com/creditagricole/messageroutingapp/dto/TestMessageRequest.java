package com.creditagricole.messageroutingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestMessageRequest {
    private String content;
    private String source;
}
