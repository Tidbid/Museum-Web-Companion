package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.dto.HallShowpiecesDto;
import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.service.HallService;
import com.romanov.rksp.museum.service.ShowpieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    //TODO html
    @GetMapping("/edit/halls/showpieces")
    public String viewShowpiecesInEditMode(@RequestParam Long hall_id, Model model) {
        model.addAttribute("hall", hallService.findHallById(hall_id));
        return "showpieces_edit";
    }

    @GetMapping("/edit/halls/orphans")
    public String viewOrphanHalls(Model model) {
        Exhibit stub = new Exhibit("Свободные Залы:", hallService.findVacantHalls());
        model.addAttribute("exhibit", stub);
        return "halls_edit";
    }

    //TODO add information about exhibitions of halls into HTML
    @GetMapping("/edit/halls/all")
    public String viewAllHallsInEditMode(Model model) {
        Exhibit stub = new Exhibit("Все Залы:", hallService.findAllHalls());
        model.addAttribute("exhibit", stub);
        return "halls_edit";
    }

    //TODO add this to edit html as a button
    // maybe it won't refresh the page?
    @GetMapping("/edit/halls/orphanize")
    public ResponseEntity<?> orphanizeHall(@RequestParam Long hall_id){
        hallService.makeOrphan(hall_id);
        return ResponseEntity.ok().build();
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

    @PostMapping("/edit/halls/save")
    public String saveHall(@ModelAttribute HallShowpiecesDto hallShowpiecesDto) {
        Hall hall = hallService.saveHallAndProcessShowpieces(
                hallShowpiecesDto.getHall(),
                hallShowpiecesDto.getShowpiecesToAdd()
        );
        return "redirect:/museum/browse/halls/showpieces?hall_id=" + hall.getId().toString();
    }

    @GetMapping("/edit/halls/delete")
    public String deleteHall(@RequestParam Long hall_id, @RequestParam Boolean erase){
        Long exh_id = hallService.deleteHallAndProcessShowpieces(hall_id, erase);
        String ret = "redirect:/museum/browse/exhibitions/halls";
        ret += (exh_id == null) ?
                "" : "?exh_id=" + exh_id;
        return ret;
    }
}
