package com.creditagricole.messageroutingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDTO {
    private Long id;

    @NotBlank(message = "L'alias est obligatoire")
    @Size(min = 3, max = 50, message = "L'alias doit contenir entre 3 et 50 caractères")
    private String alias;

    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotBlank(message = "La direction est obligatoire")
    @Pattern(regexp = "INBOUND|OUTBOUND", message = "La direction doit être soit INBOUND, soit OUTBOUND")
    private String direction;

    private String application;

    @NotBlank(message = "Le type de flux traité est obligatoire")
    @Pattern(regexp = "MESSAGE|ALERTING|NOTIFICATION", message = "Le type de flux traité doit être MESSAGE, ALERTING ou NOTIFICATION")
    private String processedFlowType;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    private String description;
}
