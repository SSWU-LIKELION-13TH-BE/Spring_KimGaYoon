package com.comment.comment_crud.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CommentRequestDto {
    private Long userId;
    private String content;
}
