package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Showpiece;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String saveExhibitionImage(Exhibit exhibit, MultipartFile exhImg);
    String saveShowpieceImage(Showpiece showpiece, MultipartFile showpieceImg);

    String getRandomExhibitionImage();

    String getDefaultShowpieceImage();
    List<String> showAllExhibitionImages();
    List<String> showAllShowpieceImages();
}
