package com.romanov.rksp.museum.repository;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface HallRepo extends JpaRepository<Hall, Long> {
    Hall findHallById(Long hallId);

    @Query(
            value = "SELECT * FROM hall WHERE exhibit_id = :exh_id;",
            nativeQuery = true
    )
    List<Hall> findHallByExhibitId(@Param("exh_id") Long exh_id);

    @Query(
            value = "SELECT * FROM hall WHERE exhibit_id IS NULL;",
            nativeQuery = true
    )
    List<Hall> findVacantHalls();

    @Modifying
    @Query(
            value = "UPDATE Hall h SET h.exhibit = :exh WHERE h.id in :id_list"
    )
    void assignExhibit(@Param("list") Collection<Long> id_list, @Param("exh") Exhibit exh);

    @Modifying
    @Query(
            value = "UPDATE Hall h set h.exhibit = null WHERE h.id IN :id_list"
    )
    void makeOrphan(@Param("id_list") Collection<Long> id_list);
}
