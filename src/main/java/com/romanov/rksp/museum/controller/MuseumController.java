package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.dto.ExhibitHallsDto;
import com.romanov.rksp.museum.dto.HallShowpiecesDto;
import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.service.ExhibitService;
import com.romanov.rksp.museum.service.HallService;
import com.romanov.rksp.museum.service.ImageService;
import com.romanov.rksp.museum.service.ShowpieceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MuseumController {
    private final ExhibitService exhibitService;

    @GetMapping("/museum")
    public String viewIndexPage(Model model) {
        model.addAttribute("active_exhibits", exhibitService.findFiveActiveExhibits());
        return "index";
    }

    @GetMapping()
    public String fallbackIndex(Model model) {
        model.addAttribute("active_exhibits", exhibitService.findFiveActiveExhibits());
        return "index";
    }
}