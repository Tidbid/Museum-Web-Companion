package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.repository.ShowpieceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowpieceService {
    private final ShowpieceRepo showpieceRepo;

    @Autowired
    public ShowpieceService(ShowpieceRepo showpieceRepo) {
        this.showpieceRepo = showpieceRepo;
    }

    public Showpiece findShowpieceById(Long showpId) {
        return showpieceRepo.findShowpieceById(showpId);
    }

    public Showpiece saveShowpiece(Showpiece showpiece) {
        return showpieceRepo.save(showpiece);
    }
}
