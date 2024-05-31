package com.kadiraksoy.partyapp.service;


import com.kadiraksoy.partyapp.dto.kafka.JoinPartyDto;
import com.kadiraksoy.partyapp.dto.party.PartyRequestDto;
import com.kadiraksoy.partyapp.dto.party.PartyResponseDto;
import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
import com.kadiraksoy.partyapp.exception.PartyNotFoundException;
//import com.kadiraksoy.partyapp.kafka.producer.KafkaProducer;
import com.kadiraksoy.partyapp.mapper.party.PartyMapper;
import com.kadiraksoy.partyapp.mapper.user.UserMapper;
import com.kadiraksoy.partyapp.model.party.Party;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.repository.PartyRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;
    private final UserService userService;
    private final EmailService emailService;
//    private final KafkaProducer kafkaProducer;
    private final UserMapper userMapper;

    public PartyService(PartyRepository partyRepository,
                        PartyMapper partyMapper,
                        UserService userService,
                        EmailService emailService,
//                        KafkaProducer kafkaProducer,
                        UserMapper userMapper) {
        this.partyRepository = partyRepository;
        this.partyMapper = partyMapper;
        this.userService = userService;
        this.emailService = emailService;
//        this.kafkaProducer = kafkaProducer;
        this.userMapper = userMapper;
    }

    public PartyResponseDto createParty(PartyRequestDto partyRequestDto){
        Party party = partyMapper.dtoToEntity(partyRequestDto);
        User user = userService.getUserById(partyRequestDto.getAdminId());
        log.info("user:" + user);

        party.setAdmin(user);
        party.setParticipants(List.of(user));
        partyRepository.save(party);
        log.info("party:" + party);

        // Kafkaya yollayıp yap.
        emailService.sendMailAllUsers("Partiye davetlisiniz:"
                + party.getTitle()
                + party.getDescription()
                + party.getEventDate());
//        kafkaProducer.sendPartyCreateMessage(partyRequestDto);

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
    public List<PartyResponseDto> getPartiesByAdminId(Long adminId) {
        List<Party> parties = partyRepository.findAllByAdmin_Id(adminId);
        if (parties.isEmpty()) {
            throw new PartyNotFoundException("No parties found for admin with id " + adminId);
        }
        return parties.stream().map(partyMapper::entityToPartyResponseDto).toList();
    }

    public List<PartyResponseDto> getActiveParties() {
        return partyRepository.findByActiveTrue().stream().map(partyMapper::entityToPartyResponseDto).toList();
    }
    public List<PartyResponseDto> getAll(){
        return partyRepository.findAll().stream().map(partyMapper::entityToPartyResponseDto).toList();
    }

    public PartyResponseDto attendParty(Long partyId, Long userId) throws MessagingException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyNotFoundException("Party with id " + partyId + " not found"));

        User user = userService.getUserById(userId);
        if (party.getParticipants().contains(user)) {
            throw new IllegalStateException("User with id " + userId + " is already attending the party");
        }
        if (party.getParticipants().size() >= party.getParticipantLimit()) {
            throw new IllegalStateException("Party with id " + partyId + " has reached its participant limit");
        }
        party.getParticipants().add(user);
        Party updatedParty = partyRepository.save(party);

        JoinPartyDto joinPartyDto = JoinPartyDto.builder()
                .title(party.getTitle())
                .description(party.getDescription())
                .eventDate(party.getEventDate())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

//        kafkaProducer.sendPartyJoinMessage(joinPartyDto);

        emailService.sendMail(
                joinPartyDto.getEmail(),
                joinPartyDto.getFirstName(),
                joinPartyDto.getLastName(),
                "Partiye katıldınız.Parti bilgileri:"
                        + joinPartyDto.getTitle()
                        + joinPartyDto.getDescription()
                        + joinPartyDto.getEventDate());

        log.info("User " + userId + " has joined the party " + partyId);

        return partyMapper.entityToPartyResponseDto(updatedParty);
    }

    public PartyResponseDto leaveParty(Long partyId, Long userId) throws MessagingException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new PartyNotFoundException("Party with id " + partyId + " not found"));

        User user = userService.getUserById(userId);
        if (!party.getParticipants().contains(user)) {
            throw new IllegalStateException("User with id " + userId + " is not attending the party");
        }

        party.getParticipants().remove(user);
        Party updatedParty = partyRepository.save(party);

        emailService.sendMail(user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                "Partiden ayrıldınız. Parti adı:" + party.getTitle());
        log.info("User " + userId + " has left the party " + partyId);

        return partyMapper.entityToPartyResponseDto(updatedParty);
    }


}
