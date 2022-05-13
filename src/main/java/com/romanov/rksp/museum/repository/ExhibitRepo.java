package com.romanov.rksp.museum.repository;

import com.romanov.rksp.museum.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExhibitRepo extends JpaRepository<Exhibit, Long> {
    Exhibit findExhibitById(Long exh_id);
}
