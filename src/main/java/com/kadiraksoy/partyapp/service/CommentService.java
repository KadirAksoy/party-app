package com.kadiraksoy.partyapp.service;


import com.kadiraksoy.partyapp.dto.comment.CommentRequestDto;
import com.kadiraksoy.partyapp.dto.comment.CommentResponseDto;
import com.kadiraksoy.partyapp.exception.CommentNotFoundException;
import com.kadiraksoy.partyapp.mapper.comment.CommentMapper;
import com.kadiraksoy.partyapp.model.comment.Comment;
import com.kadiraksoy.partyapp.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponseDto create(CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.save(commentMapper.convertToEntity(commentRequestDto));
        log.info("Yorum kaydedildi.");

        return commentMapper.convertToDTO(comment);
    }

    public List<CommentResponseDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        log.info("Retrieved all comments, count: {}", comments.size());
        return comments.stream()
                .map(commentMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommentResponseDto findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            log.info("Comment found with id: {}", id);
            return commentMapper.convertToDTO(comment.get());
        } else {
            log.warn("Comment not found with id: {}", id);
            return null; // or throw an exception
        }
    }

    public CommentResponseDto update(Long id, CommentRequestDto commentRequestDto) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            Comment updatedComment = existingComment.get();
            updatedComment.setText(commentRequestDto.getText());
            updatedComment.setUserId(commentRequestDto.getUserId());

            commentRepository.save(updatedComment);
            log.info("Comment updated with id: {}", id);
            return commentMapper.convertToDTO(updatedComment);
        } else {
            log.warn("Comment not found with id: {}", id);
            throw new CommentNotFoundException("Yorum bulunamadı.");
        }
    }

    public void delete(Long id) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            commentRepository.delete(existingComment.get());
            log.info("Comment deleted with id: {}", id);
        } else {
            log.warn("Comment not found with id: {}", id);
        }
    }

    public List<CommentResponseDto> getCommentsByPartyId(Long partyId) {
        List<Comment> comments = commentRepository.findByPartyId(partyId);
        log.info("Retrieved comments for party id: {}, count: {}", partyId, comments.size());
        return comments.stream()
                .map(commentMapper::convertToDTO)
                .collect(Collectors.toList());
    }


}
