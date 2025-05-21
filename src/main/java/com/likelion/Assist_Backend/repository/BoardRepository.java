package com.likelion.Assist_Backend.repository;

import com.likelion.Assist_Backend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardId(Long boardId);

    void deleteByBoardId(Long boardId);
}
