package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.repository.HallRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
    private final HallRepo hallRepo;

    @Override
    public Hall findHallById(Long hallId) {
        return hallRepo.findHallById(hallId);
    }

    @Override
    public List<Hall> findVacantHalls() {
        return hallRepo.findVacantHalls();
    }

    @Override
    public Hall saveHall(Hall hall) {
        return hallRepo.save(hall);
    }

    @Override
    public void deleteHallById(Long hall_id) {
        hallRepo.deleteById(hall_id);
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
