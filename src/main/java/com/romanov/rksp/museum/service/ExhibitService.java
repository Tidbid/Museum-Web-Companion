package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;

import java.util.*;

public interface ExhibitService {
    Collection<Exhibit> findAllExhibits();

    Collection<Exhibit> findAllActiveExhibits();

    Exhibit findExhibitById(Long exh_id);

    Exhibit saveExhibit(Exhibit exhibit);

    Set<Long> processHalls(Collection<Hall> halls);

    Exhibit saveExhibitAndProcessHalls(Exhibit exhibit, Collection<Hall> hallsAfterUpdate);

    void deleteExhibitById(Long id);

    void deleteExhibitAndProcessHalls(Long exh_id, Boolean erase);

    void updateImageById(Long id, String imgUrl);

    Collection<Exhibit> findFiveActiveExhibits();
}
