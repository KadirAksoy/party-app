//package com.kadiraksoy.partyapp.mapper.comment;
//
//
//import com.kadiraksoy.partyapp.dto.comment.CommentRequestDto;
//import com.kadiraksoy.partyapp.dto.comment.CommentResponseDto;
//import com.kadiraksoy.partyapp.model.comment.Comment;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CommentConverter {
//
//    private final ModelMapper modelMapper;
//
//    public CommentConverter(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }
//
//    public Comment convertToEntity(CommentRequestDto commentRequestDTO) {
//        return modelMapper.map(commentRequestDTO, Comment.class);
//    }
//
//    public CommentResponseDto convertToDTO(Comment comment) {
//        return modelMapper.map(comment, CommentResponseDto.class);
//    }
//}
