package com.creditagricole.messageroutingapp.controller;

import com.creditagricole.messageroutingapp.dto.MessageDTO;
import com.creditagricole.messageroutingapp.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "API de gestion des messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @Operation(summary = "Récupérer les messages paginés", description = "Renvoie une page de messages")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opération réussie"),
            @ApiResponse(responseCode = "400", description = "Paramètres de pagination invalides")
    })
    public ResponseEntity<Page<MessageDTO>> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "receivedDate") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir) {

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("La page doit être >= 0 et la taille > 0");
        }

        return ResponseEntity.ok(messageService.getAllMessages(page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un message par ID", description = "Renvoie un message spécifique basé sur son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message trouvé"),
            @ApiResponse(responseCode = "404", description = "Message non trouvé")
    })
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }
}
