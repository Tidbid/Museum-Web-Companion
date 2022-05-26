package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.repository.ExhibitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ExhibitService {
    private final ExhibitRepo exhibitRepo;

    @Autowired
    public ExhibitService(ExhibitRepo exhibitRepo){
        this.exhibitRepo = exhibitRepo;
    }

    public List<Exhibit> findAllExhibits() {
        return exhibitRepo.findAll();
    }

    public Exhibit findExhibitById(Long exh_id) {
        return exhibitRepo.findExhibitById(exh_id);
    }

    public Exhibit saveExhibit(Exhibit exhibit) {
        return exhibitRepo.save(exhibit);
    }

    public void deleteExhibitById(Long id) {
        exhibitRepo.deleteById(id);
    }
}
