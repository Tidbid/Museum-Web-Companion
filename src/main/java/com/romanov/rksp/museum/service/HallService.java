package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.repository.ExhibitRepo;
import com.romanov.rksp.museum.repository.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {
    private final HallRepo hallRepo;

    @Autowired
    public HallService(HallRepo hallRepo){
        this.hallRepo = hallRepo;
    }

    public Hall findHallById(Long hallId){
        return hallRepo.findHallById(hallId);
    }

    public List<Hall> findVacantHalls() {
        return hallRepo.findVacantHalls();
    }

    public Hall saveHall(Hall hall) {
        return hallRepo.save(hall);
    }

    public void deleteHallById(Long hall_id) {
        hallRepo.deleteById(hall_id);
    }

    public void assignHalls(Exhibit exhibit, List<Hall> hallsToAdd) {
        for (Hall hall : hallsToAdd) {
            hall.setExhibit(exhibit);
            hallRepo.save(hall);
        }
    }
}
