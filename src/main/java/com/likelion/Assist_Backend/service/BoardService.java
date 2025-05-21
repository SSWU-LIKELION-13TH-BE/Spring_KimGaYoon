package com.likelion.Assist_Backend.service;

import com.likelion.Assist_Backend.dto.BoardDTO;
import com.likelion.Assist_Backend.entity.Board;
import com.likelion.Assist_Backend.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class BoardService {
    private final BoardRepository boardRepository;
    private final S3Service s3Service;

    public Optional<Board> getBoard(Long boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    public Board postBoard(Board board) {
        return boardRepository.save(board);
    }

    @Transactional
    public void putBoard(BoardDTO boardDTO) {
        Board board = Board.builder()
                .boardId(boardDTO.getBoardId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .postDate(LocalDate.now())
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.deleteByBoardId(boardId);
    }

    // 이미지 포함 게시글 생성
    @Transactional
    public void ImageBoard(BoardDTO request) throws IOException {
        String savedImageURI = s3Service.upload(request.getImage());

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .image(savedImageURI)
                .build();

        boardRepository.save(board);
    }

}
