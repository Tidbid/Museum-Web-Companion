package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.service.ExhibitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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