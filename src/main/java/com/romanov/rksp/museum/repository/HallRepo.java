package com.romanov.rksp.museum.repository;

import com.romanov.rksp.museum.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HallRepo extends JpaRepository<Hall, Long> {
    Hall findHallById(Long hallId);

    @Query(
            value = "SELECT * FROM hall WHERE exhibit_id =: exh_id;",
            nativeQuery = true
    )
    List<Hall> findHallByExhibitId(@Param("exh_id") Long exh_id);
}
