package com.romanov.rksp.museum.repository;

import com.romanov.rksp.museum.model.Showpiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface ShowpieceRepo extends JpaRepository<Showpiece, Long> {
    Showpiece findShowpieceById(Long id);

    @Query(
            value = "SELECT * FROM showpiece WHERE hall_id IS NULL;",
            nativeQuery = true
    )
    List<Showpiece> findVacantShowpieces();

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE showpiece SET hall_id = ?1 WHERE id IN ?2",
            nativeQuery = true
    )
    void assignHall(Long hallId, Collection<Long> ids);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE showpiece SET hall_id = NULL WHERE id IN ?1",
            nativeQuery = true
    )
    void makeOrphan(Collection<Long> ids);

    @Transactional
    @Modifying
    @Query(value= "UPDATE showpiece SET image_url = ?1 WHERE id = ?2",
            nativeQuery = true)
    void updateImageById(String imgUrl, Long id);

    Collection<Showpiece> findAllByOrderByNameAsc();
}
