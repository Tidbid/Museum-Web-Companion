package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;

import java.util.Collection;
import java.util.List;

public interface ShowpieceService {
    Showpiece findShowpieceById(Long id);

    Showpiece saveShowpiece(Showpiece showpiece);

    void makeOrphan(Collection<Showpiece> showpieces);

    Collection<Showpiece> findVacantShowpieces();

    void deleteShowpieceById(Long id);
}
