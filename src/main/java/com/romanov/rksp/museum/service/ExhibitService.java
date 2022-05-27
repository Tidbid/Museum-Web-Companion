package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;

import java.util.*;

public interface ExhibitService {
    List<Exhibit> findAllExhibits();

    Exhibit findExhibitById(Long exh_id);

    Exhibit saveExhibit(Exhibit exhibit);

    Set<Long> processHalls(Collection<Hall> halls);

    ArrayList<Set<Long>> saveExhibitAndProcessHalls(Exhibit exhibit, Collection<Hall> hallsAfterUpdate);

    void deleteExhibitById(Long id);

    void updateImageById(Long id, String imgUrl);
}
