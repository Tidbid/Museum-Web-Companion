package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;

import java.util.Collection;
import java.util.List;

public interface ShowpieceService {
    Showpiece findShowpieceById(Long showpId);

    Showpiece saveShowpiece(Showpiece showpiece);

    void makeOrphan(Collection<Showpiece> showpieces);

    List<Showpiece> findVacantShowpieces();

    void assignHall(Hall hall, List<Showpiece> showpiecesToAdd);

    void deleteShowpieceById(Long shwp_id);
}
