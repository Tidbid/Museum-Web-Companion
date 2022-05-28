package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.dto.repository.ShowpieceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowpieceServiceImpl implements ShowpieceService {
    private final ShowpieceRepo showpieceRepo;

    @Override
    public Showpiece findShowpieceById(Long id) {
        return showpieceRepo.findShowpieceById(id);
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
    public Collection<Showpiece> findVacantShowpieces() {
        return showpieceRepo.findVacantShowpieces();
    }

    @Override
    public void deleteShowpieceById(Long id) {
        showpieceRepo.deleteById(id);
    }
}
