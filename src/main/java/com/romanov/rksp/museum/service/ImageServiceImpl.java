package com.romanov.rksp.museum.service;

import com.romanov.rksp.museum.MuseumApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    private static final String exhDir = "exh/";
    private static final String shwpDir = "shwp/";
    private static final String contentUrl = "/img/";

    @Override
    public String getRandomExhibitionImage() {
        return null;
    }

    @Override
    public String getDefaultShowpieceImage() {
        return null;
    }

    @Override
    public String saveExhibitionImage(Long id, MultipartFile exhImg) {
        String retUrl;
        try {
            if (exhImg == null)
                throw new Exception("The file is empty");
            //overwriting problem, use custom name
            String fileName =
                    StringUtils.cleanPath(exhImg.getOriginalFilename());
            String[] parts = fileName.split("[.]");
            fileName = id.toString() + "." + parts[parts.length - 1];
            byte[] bytes = exhImg.getBytes();
            Path path =
                    Paths.get(MuseumApplication.IMAGE_EXH_DIR + fileName);
            Files.write(path, bytes);
            retUrl = contentUrl + exhDir + fileName;
        } catch (Exception e) {
            log.error("Error while saving new exhibition image! With error message: {}", e.getMessage());
            retUrl = getRandomExhibitionImage();
        }
        return retUrl;
    }

    @Override
    public String saveShowpieceImage(MultipartFile showpieceImg) {
        return null;
    }

    @Override
    public List<String> showAllExhibitionImage() {
        return null;
    }

    @Override
    public List<String> showAllShowpieceImage() {
        return null;
    }
}
