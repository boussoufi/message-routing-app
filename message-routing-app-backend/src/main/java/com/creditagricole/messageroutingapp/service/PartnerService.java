package com.creditagricole.messageroutingapp.service;

import com.creditagricole.messageroutingapp.dto.PartnerDTO;
import com.creditagricole.messageroutingapp.exception.DuplicateResourceException;
import com.creditagricole.messageroutingapp.exception.ResourceNotFoundException;
import com.creditagricole.messageroutingapp.mapper.PartnerMapper;
import com.creditagricole.messageroutingapp.model.Direction;
import com.creditagricole.messageroutingapp.model.Partner;
import com.creditagricole.messageroutingapp.model.ProcessedFlowType;
import com.creditagricole.messageroutingapp.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, PartnerMapper partnerMapper) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
    }

    @Transactional(readOnly = true)
    public List<PartnerDTO> getAllPartners() {
        return partnerMapper.toDtoList(partnerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PartnerDTO getPartnerById(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id " + id));
        return partnerMapper.toDto(partner);
    }

    @Transactional
    public PartnerDTO createPartner(PartnerDTO partnerDTO) {
        if (partnerRepository.existsByAlias(partnerDTO.getAlias())) {
            throw new DuplicateResourceException("Partner with alias " + partnerDTO.getAlias() + " already exists");
        }

        Partner partner = partnerMapper.toEntity(partnerDTO);
        Partner savedPartner = partnerRepository.save(partner);
        return partnerMapper.toDto(savedPartner);
    }

    @Transactional
    public PartnerDTO updatePartner(Long id, PartnerDTO partnerDTO) {
        Partner existingPartner = partnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id: " + id));

        // Vérifier si l'alias est unique (s'il a été modifié)
        String alias = partnerDTO.getAlias();
        if (!alias.equals(existingPartner.getAlias())) {
            Optional<Partner> existingPartnerWithSameAlias = partnerRepository.findByAlias(alias);
            if (existingPartnerWithSameAlias.isPresent()) {
                throw new DuplicateResourceException("Partner with alias " + alias + " already exists");
            }
        }

        existingPartner.setAlias(partnerDTO.getAlias());
        existingPartner.setType(partnerDTO.getType());
        existingPartner.setDirection(Direction.valueOf(partnerDTO.getDirection()));
        existingPartner.setApplication(partnerDTO.getApplication());
        existingPartner.setProcessedFlowType(ProcessedFlowType.valueOf(partnerDTO.getProcessedFlowType()));
        existingPartner.setDescription(partnerDTO.getDescription());

        Partner updatedPartner = partnerRepository.save(existingPartner);
        return partnerMapper.toDto(updatedPartner);
    }

    @Transactional
    public void deletePartner(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id: " + id));

        partnerRepository.deleteById(id);
    }

}
