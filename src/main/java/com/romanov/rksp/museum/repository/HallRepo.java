package com.romanov.rksp.museum.repository;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface HallRepo extends JpaRepository<Hall, Long> {
    Hall findHallById(Long hallId);

    @Query(
            value = "SELECT * FROM hall WHERE exhibit_id = :exh_id",
            nativeQuery = true
    )
    List<Hall> findHallByExhibitId(@Param("exh_id") Long exh_id);

    @Query(
            value = "SELECT * FROM hall WHERE exhibit_id IS NULL",
            nativeQuery = true
    )
    List<Hall> findVacantHalls();

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE hall SET exhibit_id = ?1 WHERE id IN ?2",
            nativeQuery = true
    )
    void assignExhibit(Long exhId, Collection<Long> idColl);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE hall SET exhibit_id = NULL WHERE id IN ?1",
            nativeQuery = true
    )
    void makeOrphan(Collection<Long> idColl);
}
