package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;

import java.util.Collection;
import java.util.List;

public interface HallService {
    Hall findHallById(Long hallId);

    List<Hall> findVacantHalls();

    Hall saveHall(Hall hall);

    void deleteHallById(Long hall_id);

    void assignExhibit(Long exhId, Collection<Long> hallsToAdd);

    void makeOrphan(Collection<Long> poorOrphans);
}
