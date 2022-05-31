package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.repository.ExhibitRepo;
import com.romanov.rksp.museum.repository.HallRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExhibitServiceImpl implements ExhibitService {
    private final ExhibitRepo exhibitRepo;

    private final HallRepo hallRepo;

    @Override
    public Collection<Exhibit> findAllExhibits() {
        return exhibitRepo.findAll();
    }

    @Override
    public Collection<Exhibit> findAllActiveExhibits() {
        return exhibitRepo.findAllActive();
    }

    @Override
    public Exhibit findExhibitById(Long exh_id) {
        return exhibitRepo.findExhibitById(exh_id);
    }

    @Override
    public Exhibit saveExhibit(Exhibit exhibit) {
        return exhibitRepo.save(exhibit);
    }

    //TODO maybe make it generic and put into utility class (types with getId() method)
    @Override
    public Set<Long> processHalls(Collection<Hall> halls) {
        Set<Long> ret;
        if (halls != null) {
            ret = new LinkedHashSet<>(halls.size());
            for (Hall hall : halls)
                ret.add(hall.getId());
        } else {
            ret = new LinkedHashSet<>(0);
        }
        return ret;
    }

    @Override
    public Exhibit saveExhibitAndProcessHalls(Exhibit exhibit, Collection<Hall> hallsAfterUpdate) {
        exhibitRepo.save(exhibit);
        //this set contains elements that
        //should be linked to that exhibit
        //some elements may already be linked
        //others - may be not
        Set<Long> hallsToAdd = this.processHalls(hallsAfterUpdate);
        //this set should contain elements that
        //are no longer linked to that exhibit (hence orphans)
        //but initially it contains elements that were linked
        //to the previous version of that exhibit
        //among which some should still be linked to this version
        //others should be detached from it
        Set<Long> orphans = this.processHalls(exhibit.getHalls());
        Set<Long> aux = new LinkedHashSet<>(orphans.size());
        aux.addAll(orphans);
        //we do things effectively in this neighborhood!
        orphans.removeAll(hallsToAdd);
        hallsToAdd.removeAll(aux);
        //in the end we get:
        //orphans = (orphans(original) / hallsToAdd(original)) - set of elements that should be detached
        //hallsToAdd = (hallsToAdd(original) / orphans(original)) - set of elements that should be attached
        if (!hallsToAdd.isEmpty())
            hallRepo.assignExhibit(exhibit.getId(), hallsToAdd);
        if (!orphans.isEmpty())
            hallRepo.makeOrphan(orphans);
        return exhibit;
    }

    @Override
    public void deleteExhibitById(Long id) {
        exhibitRepo.deleteById(id);
    }

    @Override
    public void deleteExhibitAndProcessHalls(Long exh_id, Boolean erase) {
        Set<Long> halls = processHalls(exhibitRepo.findExhibitById(exh_id).getHalls());
        if (erase) {
            hallRepo.deleteAllById(halls);
        } else {
            //TODO can be made more efficient by
            // ADD CONSTRAINT ON DELETE SET NULL
            // in the database
            hallRepo.makeOrphan(halls);
        }
        exhibitRepo.deleteById(exh_id);
    }

    @Override
    public void updateImageById(Long id, String imgUrl) {
        exhibitRepo.updateImageUrlById(imgUrl, id);
    }

    @Override
    public Collection<Exhibit> findFiveActiveExhibits() {
        return exhibitRepo.findFiveActiveExhibits();
    }
}
