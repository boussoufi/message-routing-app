package com.creditagricole.messageroutingapp.mapper;

import com.creditagricole.messageroutingapp.dto.PartnerDTO;
import com.creditagricole.messageroutingapp.model.Direction;
import com.creditagricole.messageroutingapp.model.Partner;
import com.creditagricole.messageroutingapp.model.ProcessedFlowType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PartnerMapper {

    public Partner toEntity(PartnerDTO dto) {
        if (dto == null) {
            return null;
        }

        Partner partner = new Partner();
        partner.setId(dto.getId());
        partner.setAlias(dto.getAlias());
        partner.setType(dto.getType());

        // Conversion d'enum avec vérification
        if (dto.getDirection() != null) {
            partner.setDirection(Direction.valueOf(dto.getDirection()));
        }

        partner.setApplication(dto.getApplication());

        // Conversion d'enum avec vérification
        if (dto.getProcessedFlowType() != null) {
            partner.setProcessedFlowType(ProcessedFlowType.valueOf(dto.getProcessedFlowType()));
        }

        partner.setDescription(dto.getDescription());
        return partner;
    }

    public PartnerDTO toDto(Partner partner) {
        if (partner == null) {
            return null;
        }

        PartnerDTO dto = new PartnerDTO();
        dto.setId(partner.getId());
        dto.setAlias(partner.getAlias());
        dto.setType(partner.getType());

        if (partner.getDirection() != null) {
            dto.setDirection(partner.getDirection().name());
        }

        dto.setApplication(partner.getApplication());

        if (partner.getProcessedFlowType() != null) {
            dto.setProcessedFlowType(partner.getProcessedFlowType().name());
        }

        dto.setDescription(partner.getDescription());
        return dto;
    }

    public List<PartnerDTO> toDtoList(List<Partner> partners) {
        if (partners == null) {
            return Collections.emptyList();
        }

        List<PartnerDTO> dtoList = new ArrayList<>(partners.size());
        for (Partner partner : partners) {
            dtoList.add(toDto(partner));
        }
        return dtoList;
    }
}
