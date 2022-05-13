package com.romanov.rksp.museum.repository;

import com.romanov.rksp.museum.model.Showpiece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowpieceRepo extends JpaRepository<Showpiece, Long> {
    Showpiece findShowpieceById(Long showpId);
}
