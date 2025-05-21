package com.likelion.Assist_Backend.controller;

import com.likelion.Assist_Backend.dto.BoardDTO;
import com.likelion.Assist_Backend.entity.Board;
import com.likelion.Assist_Backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j

public class BoardController {
    private final BoardService boardService;

    @GetMapping("/getBoard")
    public Optional<Board> getBoard(@RequestParam(name = "boardId") Long boardId) {
        return boardService.getBoard(boardId);
    }

    @PostMapping
    public void postBoard(@RequestBody BoardDTO boardDTO) {
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        boardService.postBoard(board);
    }

    @PutMapping("/putBoard")
    public void putBoard(@RequestBody BoardDTO boardDTO) {
        boardService.putBoard(boardDTO);
    }

    @DeleteMapping("/deleteBoard/{boardId}")
    public void deleteBoard(@PathVariable(name = "boardId") Long boardId) {
        boardService.deleteBoard(boardId);
    }

    //이미지 포함 게시글 올리기
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@ModelAttribute BoardDTO imageboardDTO) {
        try{
            BoardDTO requset= BoardDTO.builder()
                    .title(imageboardDTO.getTitle())
                    .content(imageboardDTO.getContent())
                    .image(imageboardDTO.getImage())
                    .writer(imageboardDTO.getWriter())
                    .build();
            boardService.ImageBoard(requset);

            return ResponseEntity.ok("파일 업로드 성공");
        } catch (Exception e){
            log.error("파일 업로드 실패", e);
            return ResponseEntity.status(400).build();
        }

    }
}


