package com.comment.comment_crud.repository;

import com.comment.comment_crud.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}