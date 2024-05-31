package com.kadiraksoy.partyapp.mapper.party;

import com.kadiraksoy.partyapp.dto.party.PartyRequestDto;
import com.kadiraksoy.partyapp.dto.party.PartyResponseDto;
import com.kadiraksoy.partyapp.mapper.user.UserMapper;
import com.kadiraksoy.partyapp.model.party.Party;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartyMapper {

    private  UserMapper userMapper = null;

    public PartyMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public  Party dtoToEntity(PartyRequestDto partyRequestDto){
        return Party.builder()
                .title(partyRequestDto.getTitle())
                .description(partyRequestDto.getDescription())
                .eventDate(partyRequestDto.getEventDate())
                .participantLimit(partyRequestDto.getParticipantLimit())
                .imageId(partyRequestDto.getImageId())
                .active(true)
                .build();
    }

    public  PartyResponseDto entityToPartyResponseDto(Party party){
        return PartyResponseDto.builder()
                .id(party.getId())
                .createdTime(party.getCreatedTime())
                .updatedTime(party.getUpdatedTime())
                .title(party.getTitle())
                .description(party.getDescription())
                .eventDate(party.getEventDate())
                .participantLimit(party.getParticipantLimit())
                .imageId(party.getImageId())
                .participants(userMapper.toUserResponseDtoList(party.getParticipants()))
                .admin(userMapper.toUserResponseDto(party.getAdmin()))
                .active(true)
                .build();
    }

    public  List<PartyResponseDto> toPartyResponseDtoList(List<Party> parties) {
        return parties.stream()
                .map(this::entityToPartyResponseDto)
                .collect(Collectors.toList());
    }
}
