package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.dto.HallShowpiecesDto;
import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.service.HallService;
import com.romanov.rksp.museum.service.ShowpieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/museum")
public class HallController {
    private final HallService hallService;
    private final ShowpieceService showpieceService;

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

    //TODO add field that will
    // allow manager to specify an exhibit
    // from this view
    @GetMapping("/edit/halls/create")
    public String showNewHallForm(Model model) {
        model.addAttribute("hallShowpiecesDto", new HallShowpiecesDto(new Hall(), new ArrayList<>()));
        model.addAttribute("vacantShowpieces", showpieceService.findVacantShowpieces());
        model.addAttribute("head", "Создать");
        return "modify_form_hall";
    }

    @GetMapping("/edit/halls/update")
    public String updateHall(@RequestParam Long hall_id, Model model) {
        Hall hall = hallService.findHallById(hall_id);
        model.addAttribute("hallShowpiecesDto",
                new HallShowpiecesDto(hall, (List<Showpiece>) hall.getShowpieces()));
        model.addAttribute("vacantShowpieces", showpieceService.findVacantShowpieces());
        model.addAttribute("head", "Изменить");
        return "modify_form_hall";
    }

    //TODO check for any null fields if updated
    @PostMapping("/edit/halls/save")
    public String saveHall(@ModelAttribute HallShowpiecesDto hallShowpiecesDto) {
        Hall hall = hallShowpiecesDto.getHall();
        hallService.saveHall(hall);
        showpieceService.assignHall(hall, hallShowpiecesDto.getShowpiecesToAdd());
        return "redirect:/museum/browse/halls/showpieces?hall_id=" + hall.getId().toString();
    }

    @GetMapping("/edit/halls/delete")
    public String deleteHall(@RequestParam Long hall_id, Model model){
        Hall hall = hallService.findHallById(hall_id);
        String ret = "redirect:/museum/browse/";
        ret += (hall.getExhibit() == null) ?
                "halls/orphans" : "exhibitions/halls?exh_id=" + hall.getExhibit().getId();
        //TODO supplant this inefficient garbage with
        // ADD CONSTRAINT ON DELETE SET NULL
        // in the database
        showpieceService.makeOrphan(hall.getShowpieces());
        hallService.deleteHallById(hall_id);
        return ret;
    }
}
