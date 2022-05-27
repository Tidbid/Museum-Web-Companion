package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.repository.ExhibitRepo;
import com.romanov.rksp.museum.repository.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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

    public void assignExhibit(Exhibit exhibit, List<Hall> hallsToAdd) {
        //hallRepo.assignExhibit(hallsToAdd, exhibit);
        for (Hall hall : hallsToAdd) {
            hall.setExhibit(exhibit);
            hallRepo.save(hall);
        }
    }
    public void makeOrphan(Collection<Hall> poorOrphans) {
        for (Hall hall : poorOrphans) {
            hall.setExhibit(null);
            //TODO look into JPA more closely
            // this entity should be persisted
            // but the session does not flush and
            // changes are not committed without this save
            // (should remove it but don't care to rn)
            hallRepo.save(hall);
        }
    }
}
