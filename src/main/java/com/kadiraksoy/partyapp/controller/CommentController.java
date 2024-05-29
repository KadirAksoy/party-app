package com.kadiraksoy.partyapp.controller;

import com.kadiraksoy.partyapp.dto.comment.CommentRequestDto;
import com.kadiraksoy.partyapp.dto.comment.CommentResponseDto;
import com.kadiraksoy.partyapp.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/save")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto createdComment = commentService.create(commentRequestDto);
        log.info("Comment created with id: {}", createdComment.getId());
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> comments = commentService.findAll();
        log.info("Retrieved all comments, count: {}", comments.size());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        CommentResponseDto comment = commentService.findById(id);
        if (comment != null) {
            log.info("Comment found with id: {}", id);
            return ResponseEntity.ok(comment);
        } else {
            log.warn("Comment not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto updatedComment = commentService.update(id, commentRequestDto);
        if (updatedComment != null) {
            log.info("Comment updated with id: {}", id);
            return ResponseEntity.ok(updatedComment);
        } else {
            log.warn("Comment not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        log.info("Comment deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPartyId(@PathVariable Long partyId) {
        List<CommentResponseDto> comments = commentService.getCommentsByPartyId(partyId);
        log.info("Retrieved comments for party id: {}, count: {}", partyId, comments.size());
        return ResponseEntity.ok(comments);
    }
}
