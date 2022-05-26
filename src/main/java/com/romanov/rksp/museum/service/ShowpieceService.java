package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.repository.ShowpieceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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

    public void makeOrphan(Collection<Showpiece> showpieces) {
        for (Showpiece showpiece : showpieces) {
            showpiece.setHall(null);
            showpieceRepo.save(showpiece);
        }
    }

    public List<Showpiece> findVacantShowpieces() {
        return showpieceRepo.findVacantShowpieces();
    }

    public void assignHall(Hall hall, List<Showpiece> showpiecesToAdd) {
        for (Showpiece showpiece : showpiecesToAdd) {
            showpiece.setHall(hall);
            //TODO look into JPA more closely
            // this entity should be persisted
            // but the session does not flush and
            // changes are not committed without this save
            // (should remove it but don't care to rn)
            showpieceRepo.save(showpiece);
        }
    }

    public void deleteShowpieceById(Long shwp_id) {
        showpieceRepo.deleteById(shwp_id);
    }
}
