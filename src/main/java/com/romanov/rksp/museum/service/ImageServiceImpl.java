package com.romanov.rksp.museum.service;

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
    private static final String absolutePath = Paths.get(
            "src",
            "main",
            "resources",
            "static",
            "images").toFile().getAbsolutePath();
    private static final String exhImageDir = "exh";
    private static final String showpieceImageDir = "showp";
    //specif in the application.prop file
    private static final String staticContentUrl = "/content/images/";

    @Override
    public String getRandomExhibitionImage() {
        return null;
    }

    @Override
    public String getDefaultShowpieceImage() {
        return null;
    }

    @Override
    public String saveExhibitionImage(MultipartFile exhImg) {
        String retUrl;
        try {
            if (exhImg == null)
                throw new Exception("The file is empty");
            //overwriting problem, use custom name
            String fileName =
                    StringUtils.cleanPath(exhImg.getOriginalFilename());
            byte[] bytes = exhImg.getBytes();
            Path path =
                    Paths.get(absolutePath + File.separator +
                            exhImageDir + File.separator + fileName);
            Files.write(path, bytes);
            retUrl = staticContentUrl + exhImageDir + '/' + fileName;
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
