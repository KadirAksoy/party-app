package com.kadiraksoy.partyapp.mapper.comment;


import com.kadiraksoy.partyapp.dto.comment.CommentRequestDto;
import com.kadiraksoy.partyapp.dto.comment.CommentResponseDto;
import com.kadiraksoy.partyapp.mapper.party.PartyMapper;
import com.kadiraksoy.partyapp.model.comment.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {



    public Comment convertToEntity(CommentRequestDto commentRequestDTO) {
        return Comment.builder()
                .text(commentRequestDTO.getText())
                .partyId(commentRequestDTO.getPartyId())
                .userId(commentRequestDTO.getUserId())
                .build();
    }

    public CommentResponseDto convertToDTO(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .user(comment.getUserId())
                .party(comment.getPartyId())
                .createdTime(comment.getCreatedTime())
                .updatedTime(comment.getUpdatedTime())
                .build();
    }
}
