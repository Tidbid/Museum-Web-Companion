package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;

import java.util.Collection;
import java.util.List;

public interface ShowpieceService {
    Showpiece findShowpieceById(Long id);

    Showpiece saveShowpiece(Showpiece showpiece);

    void makeOrphan(Long shwp_id);

    Collection<Showpiece> findVacantShowpieces();

    void updateImageById(Long id, String imgUrl);

    Long deleteShowpieceById(Long id);

    Collection<Showpiece> findAllShowpieces();
}
