package com.vaultx.vaultxsp.services.impl;

import com.vaultx.vaultxsp.dtos.SocietyDto.*;
import com.vaultx.vaultxsp.models.Society;
import com.vaultx.vaultxsp.repositories.SocietyRepository;
import com.vaultx.vaultxsp.services.SocietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SocietyServiceImpl implements SocietyService {

    private final SocietyRepository societyRepository;

    @Override
    public SocietyResponseDto getSociety() {
        Society society = societyRepository.findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Society not found"));
        return toDto(society);
    }

    @Override
    public SocietyResponseDto createSociety(CreateSocietyDto dto, String adminUserId) {
        if (societyRepository.count() > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Society already exists");
        }

        Society society = new Society();
        society.setSocietyId("SOC_" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase());
        society.setName(dto.getName());
        society.setAddress(dto.getAddress());
        society.setCity(dto.getCity());
        society.setState(dto.getState());
        society.setPostalCode(dto.getPostalCode());
        society.setUserId(adminUserId);

        societyRepository.save(society);
        return toDto(society);
    }

    @Override
    public SocietyResponseDto updateSociety(UpdateSocietyDto dto) {
        Society society = societyRepository.findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Society not found"));

        if (dto.getName() != null)       society.setName(dto.getName());
        if (dto.getAddress() != null)    society.setAddress(dto.getAddress());
        if (dto.getCity() != null)       society.setCity(dto.getCity());
        if (dto.getState() != null)      society.setState(dto.getState());
        if (dto.getPostalCode() != null) society.setPostalCode(dto.getPostalCode());

        societyRepository.save(society);
        return toDto(society);
    }

    private SocietyResponseDto toDto(Society society) {
        SocietyResponseDto dto = new SocietyResponseDto();
        dto.setSocietyId(society.getSocietyId());
        dto.setName(society.getName());
        dto.setAddress(society.getAddress());
        dto.setCity(society.getCity());
        dto.setState(society.getState());
        dto.setPostalCode(society.getPostalCode());
        return dto;
    }
}
