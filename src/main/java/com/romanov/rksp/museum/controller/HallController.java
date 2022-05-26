package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.dto.ExhibitHallsDto;
import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/museum")
public class HallController {
    private final HallService hallService;

    @GetMapping("/browse/halls/showpieces")
    public String viewShowpieces(@RequestParam Long hall_id, Model model) {
        model.addAttribute("hall", hallService.findHallById(hall_id));
        return "showpieces";
    }

    @GetMapping("/browse/halls/orphans")
    public String viewOrphanHalls(Model model) {
        //Using the same view that
        //displays halls of an exhibit
        //but here we display halls with
        //no exhibit assigned
        Exhibit stub = new Exhibit("Свободные Залы:", hallService.findVacantHalls());
        model.addAttribute("exhibit", stub);
        return "halls";
    }
}
