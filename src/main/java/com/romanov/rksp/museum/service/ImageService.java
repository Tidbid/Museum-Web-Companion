package com.romanov.rksp.museum.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String saveExhibitionImage(MultipartFile exhImg);
    String saveShowpieceImage(MultipartFile showpieceImg);

    String getRandomExhibitionImage();

    String getDefaultShowpieceImage();
    List<String> showAllExhibitionImage();
    List<String> showAllShowpieceImage();
}
