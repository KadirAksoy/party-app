package com.kadiraksoy.partyapp.repository;

import com.kadiraksoy.partyapp.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPartyId(Long partyId);
}
