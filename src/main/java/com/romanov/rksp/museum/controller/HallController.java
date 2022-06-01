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
    public String viewShowpieces(@RequestParam(required = false) Long hall_id, Model model) {
        if (hall_id == null)
            return "redirect:/museum/edit/showpieces/orphans";
        model.addAttribute("hall", hallService.findHallById(hall_id));
        return "showpieces";
    }

    @GetMapping("/edit/halls/showpieces")
    public String viewShowpiecesInEditMode(@RequestParam(required = false) Long hall_id, Model model) {
        if (hall_id == null)
            return "redirect:/museum/edit/showpieces/orphans";
        model.addAttribute("hall", hallService.findHallById(hall_id));
        return "showpieces_edit";
    }

    @GetMapping("/edit/halls/orphans")
    public String viewOrphanHalls(Model model) {
        Exhibit stub = new Exhibit("Свободные Залы", hallService.findVacantHalls());
        model.addAttribute("exhibit", stub);
        return "halls_edit";
    }

    //TODO add information about exhibitions
    @GetMapping("/edit/halls/all")
    public String viewAllHallsInEditMode(Model model) {
        Exhibit stub = new Exhibit("Все Залы", hallService.findAllHalls());
        model.addAttribute("exhibit", stub);
        return "halls_edit";
    }

    @GetMapping("/edit/halls/orphanize")
    public String orphanizeHall(@RequestParam Long hall_id, @RequestParam Long exh_id){
        hallService.makeOrphan(hall_id);
        return "redirect:/museum/edit/exhibitions/halls?exh_id=" + exh_id;
    }

    @GetMapping("/edit/halls/create")
    public String showNewHallForm(@RequestParam(required = false) Long exh_id, Model model) {
        Hall hall = new Hall();
        if (exh_id != null) {
            hall.setExhibit(new Exhibit());
            hall.getExhibit().setId(exh_id);
        }
        model.addAttribute("hallShowpiecesDto", new HallShowpiecesDto(hall, new ArrayList<>()));
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

    @PostMapping("/edit/halls/save")
    public String saveHall(@ModelAttribute HallShowpiecesDto hallShowpiecesDto) {
        Hall hall = hallService.saveHallAndProcessShowpieces(
                hallShowpiecesDto.getHall(),
                hallShowpiecesDto.getShowpiecesToAdd()
        );
        return "redirect:/museum/edit/halls/showpieces?hall_id=" + hall.getId().toString();
    }

    @GetMapping("/edit/halls/delete")
    public String deleteHall(@RequestParam Long hall_id, @RequestParam Boolean erase){
        Long exh_id = hallService.deleteHallAndProcessShowpieces(hall_id, erase);
        String ret = "redirect:/museum/edit/exhibitions/halls";
        ret += (exh_id == null) ?
                "" : "?exh_id=" + exh_id;
        return ret;
    }
}
