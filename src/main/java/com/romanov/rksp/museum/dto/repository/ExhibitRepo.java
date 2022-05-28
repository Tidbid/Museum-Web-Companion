package com.romanov.rksp.museum.dto.repository;

import com.romanov.rksp.museum.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExhibitRepo extends JpaRepository<Exhibit, Long> {
    Exhibit findExhibitById(Long exhId);

    @Transactional
    @Modifying
    @Query(value= "UPDATE exhibit SET image_url = ?1 WHERE id = ?2",
            nativeQuery = true)
    void updateImageUrlById(String imgUrl, Long id);
}
