package com.vaultx.vaultxsp.services;

import com.vaultx.vaultxsp.dtos.SocietyDto.*;

public interface SocietyService {

    SocietyResponseDto getSociety();

    SocietyResponseDto createSociety(CreateSocietyDto dto, String adminUserId);

    SocietyResponseDto updateSociety(UpdateSocietyDto dto);
}
