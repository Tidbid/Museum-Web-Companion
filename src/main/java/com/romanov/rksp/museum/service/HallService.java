package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface HallService {
    Hall findHallById(Long id);

    Collection<Hall> findVacantHalls();

    Hall saveHall(Hall hall);

    Set<Long> processShowpieces(Collection<Showpiece> showpieces);

    Hall saveHallAndProcessShowpieces(Hall hall, Collection<Showpiece> showpiecesAfterUpdate);

    void deleteHallById(Long id);

    Long deleteHallAndProcessShowpieces(Long hall_id, Boolean erase);

    void assignExhibit(Long exhId, Collection<Long> hallsToAdd);

    void makeOrphan(Collection<Long> poorOrphans);

    void makeOrphan(Long hall_id);

    Collection<Hall> findAllHalls();
}
