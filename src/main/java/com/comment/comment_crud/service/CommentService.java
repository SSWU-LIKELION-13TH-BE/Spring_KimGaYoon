package com.comment.comment_crud.service;

import com.comment.comment_crud.dto.CommentRequestDto;
import com.comment.comment_crud.dto.CommentResponseDto;
import com.comment.comment_crud.entity.Comment;
import com.comment.comment_crud.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    public CommentResponseDto save(CommentRequestDto dto) {
        Comment comment = new Comment();
        comment.setUserId(dto.getUserId());
        comment.setContent(dto.getContent());
        Comment saved = commentRepository.save(comment);
        return convertToResponseDto(saved);
    }

    public Optional<CommentResponseDto> findById(Long id) {
        return commentRepository.findById(id)
                .map(this::convertToResponseDto);
    }

    public CommentResponseDto update(Long id, CommentRequestDto dto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(dto.getContent());
            Comment updated = commentRepository.save(comment);
            return convertToResponseDto(updated);
        }
        return null;
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    private CommentResponseDto convertToResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getLikes(),
                comment.getTime().format(formatter)
        );
    }
}
