package com.romanov.rksp.museum.repository;

import com.romanov.rksp.museum.model.Showpiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ShowpieceRepo extends JpaRepository<Showpiece, Long> {
    Showpiece findShowpieceById(Long showpId);

    @Query(
            value = "SELECT * FROM showpiece WHERE hall_id IS NULL;",
            nativeQuery = true
    )
    List<Showpiece> findVacantShowpieces();
}
