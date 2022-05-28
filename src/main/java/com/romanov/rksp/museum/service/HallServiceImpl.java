package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.dto.repository.HallRepo;
import com.romanov.rksp.museum.dto.repository.ShowpieceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
    private final HallRepo hallRepo;

    private final ShowpieceRepo showpieceRepo;

    @Override
    public Hall findHallById(Long id) {
        return hallRepo.findHallById(id);
    }

    @Override
    public Collection<Hall> findVacantHalls() {
        return hallRepo.findVacantHalls();
    }

    @Override
    public Hall saveHall(Hall hall) {
        return hallRepo.save(hall);
    }

    //TODO maybe make it generic and put into utility class (types with getId() method)
    @Override
    public Set<Long> processShowpieces(Collection<Showpiece> showpieces) {
        Set<Long> ret;
        if (showpieces != null) {
            ret = new LinkedHashSet<>(showpieces.size());
            for (Showpiece showpiece : showpieces)
                ret.add(showpiece.getId());
        } else {
            ret = new LinkedHashSet<>(0);
        }
        return ret;
    }

    @Override
    public Hall saveHallAndProcessShowpieces(Hall hall, Collection<Showpiece> showpiecesAfterUpdate) {
        hallRepo.save(hall);
        Set<Long> showpiecesToAdd = this.processShowpieces(showpiecesAfterUpdate);
        Set<Long> orphans = this.processShowpieces(hall.getShowpieces());
        Set<Long> aux = new LinkedHashSet<>(orphans.size());
        aux.addAll(orphans);
        orphans.removeAll(showpiecesToAdd);
        showpiecesToAdd.removeAll(aux);
        if (!showpiecesToAdd.isEmpty())
            showpieceRepo.assignHall(hall.getId(), showpiecesToAdd);
        if (!orphans.isEmpty())
            showpieceRepo.makeOrphan(orphans);
        return hall;
    }

    @Override
    public void deleteHallById(Long id) {
        hallRepo.deleteById(id);
    }

    @Override
    public Long deleteHallAndProcessShowpieces(Long hall_id, Boolean erase) {
        Hall hall = hallRepo.findHallById(hall_id);
        Long exh_id = (hall.getExhibit() == null) ? null : hall.getExhibit().getId();
        //TODO may be removed if
        // CONSTRAINT ON DELETE SET TO NULL
        // is added to the DB
        Set<Long> showpieces = processShowpieces(hall.getShowpieces());
        if (erase) {
            showpieceRepo.deleteAllById(showpieces);
        } else {
            showpieceRepo.makeOrphan(showpieces);
        }
        hallRepo.deleteById(hall_id);
        return exh_id;
    }

    @Override
    public void assignExhibit(Long exhId, Collection<Long> hallsToAdd) {
        if (hallsToAdd.isEmpty())
            return;
        hallRepo.assignExhibit(exhId, hallsToAdd);
    }

    @Override
    public void makeOrphan(Collection<Long> poorOrphans) {
        if (poorOrphans.isEmpty())
            return;
        hallRepo.makeOrphan(poorOrphans);
    }
}
