package com.kadiraksoy.partyapp.service;


import com.kadiraksoy.partyapp.dto.party.PartyRequestDto;
import com.kadiraksoy.partyapp.dto.party.PartyResponseDto;
import com.kadiraksoy.partyapp.exception.PartyNotFoundException;
import com.kadiraksoy.partyapp.mapper.party.PartyMapper;
import com.kadiraksoy.partyapp.model.party.Party;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;
    private final UserService userService;
    private final EmailService emailService;

    public PartyService(PartyRepository partyRepository,
                        PartyMapper partyMapper,
                        UserService userService,
                        EmailService emailService) {
        this.partyRepository = partyRepository;
        this.partyMapper = partyMapper;
        this.userService = userService;
        this.emailService = emailService;
    }

    public PartyResponseDto createParty(PartyRequestDto partyRequestDto){
        Party party = partyMapper.dtoToEntity(partyRequestDto);
        User user = userService.getUserById(partyRequestDto.getAdminId());
        log.info("user:" + user);

        party.setAdmin(user);
        party.setParticipants(List.of(user));
        partyRepository.save(party);
        log.info("party:" + party);

        return partyMapper.entityToPartyResponseDto(party);
    }

    public PartyResponseDto updateParty(Long partyId, PartyRequestDto partyRequestDto) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyNotFoundException("Party with id " + partyId + " not found"));

        User user = userService.getUserById(partyRequestDto.getAdminId());

        party.setParticipantLimit(partyRequestDto.getParticipantLimit());
        party.setTitle(partyRequestDto.getTitle());
        party.setImageId(partyRequestDto.getImageId());
        party.setDescription(partyRequestDto.getDescription());
        party.setEventDate(partyRequestDto.getEventDate());

        return partyMapper.entityToPartyResponseDto(partyRepository.save(party));
    }

    public void deleteParty(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyNotFoundException("Party with id " + partyId + " not found"));
        partyRepository.delete(party);
    }

    public PartyResponseDto getParty(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyNotFoundException("Party with id " + partyId + " not found"));

        return partyMapper.entityToPartyResponseDto(party);
    }

    public List<PartyResponseDto> getAll(){
        return partyRepository.findAll().stream().map(partyMapper::entityToPartyResponseDto).toList();
    }


}
