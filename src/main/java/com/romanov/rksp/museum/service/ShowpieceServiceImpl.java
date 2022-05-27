package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.repository.ShowpieceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowpieceServiceImpl implements ShowpieceService {
    private final ShowpieceRepo showpieceRepo;

    @Override
    public Showpiece findShowpieceById(Long showpId) {
        return showpieceRepo.findShowpieceById(showpId);
    }

    @Override
    public Showpiece saveShowpiece(Showpiece showpiece) {
        return showpieceRepo.save(showpiece);
    }

    @Override
    public void makeOrphan(Collection<Showpiece> showpieces) {
        for (Showpiece showpiece : showpieces) {
            showpiece.setHall(null);
            showpieceRepo.save(showpiece);
        }
    }

    @Override
    public List<Showpiece> findVacantShowpieces() {
        return showpieceRepo.findVacantShowpieces();
    }

    @Override
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

    @Override
    public void deleteShowpieceById(Long shwp_id) {
        showpieceRepo.deleteById(shwp_id);
    }
}
