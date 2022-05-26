package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.service.ShowpieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/museum")
public class ShowpieceController {
    private final ShowpieceService showpieceService;

    @GetMapping("/browse/showpieces/more")
    public String viewShowpieceMore(@RequestParam Long shwp_id, Model model) {
        model.addAttribute("showpiece", showpieceService.findShowpieceById(shwp_id));
        return "showpiece_more";
    }

    @GetMapping("/browse/showpieces/orphans")
    public String viewOrphanShowpieces(Model model) {
        //Using the same view that
        //displays showpieces of a hall
        //but here we display showpieces with
        //no hall assigned
        Hall stub = new Hall("Свободные Экспонаты:", showpieceService.findVacantShowpieces());
        model.addAttribute("hall", stub);
        return "showpieces";
    }
}
