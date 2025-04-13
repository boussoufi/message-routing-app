package com.creditagricole.messageroutingapp.controller;

import com.creditagricole.messageroutingapp.dto.PartnerDTO;
import com.creditagricole.messageroutingapp.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@Tag(name = "Partenaires", description = "API de gestion des partenaires MQ")
public class PartnerController {
    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les partenaires", description = "Retourne la liste de tous les partenaires MQ enregistrés")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des partenaires récupérée avec succès")
    })
    public ResponseEntity<List<PartnerDTO>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un partenaire par ID", description = "Retourne un partenaire spécifique basé sur son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partenaire trouvé"),
            @ApiResponse(responseCode = "404", description = "Partenaire non trouvé")
    })
    public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerService.getPartnerById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau partenaire", description = "Crée un nouveau partenaire MQ dans le système")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Partenaire créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Un partenaire avec cet alias existe déjà")
    })
    public ResponseEntity<PartnerDTO> createPartner(@Valid @RequestBody PartnerDTO partnerDTO) {
        PartnerDTO createdPartner = partnerService.createPartner(partnerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPartner);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un partenaire", description = "Met à jour un partenaire existant basé sur son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partenaire mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Partenaire non trouvé"),
            @ApiResponse(responseCode = "409", description = "Conflit avec un autre partenaire existant")
    })
    public ResponseEntity<PartnerDTO> updatePartner(@PathVariable Long id, @Valid @RequestBody PartnerDTO partnerDTO) {
        return ResponseEntity.ok(partnerService.updatePartner(id, partnerDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un partenaire", description = "Supprime un partenaire existant basé sur son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Partenaire supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Partenaire non trouvé")
    })
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }
}
