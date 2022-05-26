package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/museum")
public class HallController {
    private final HallService hallService;

    @GetMapping("/edit/halls/create")
    public String showNewHallForm(Model model) {
        model.addAttribute("hall", new Hall());
        model.addAttribute("head", "Создать");
        return "modify_form_hall";
    }

    @GetMapping("/edit/halls/update")
    public String updateHall(@RequestParam Long hall_id, Model model) {
        Hall hall = hallService.findHallById(hall_id);
        model.addAttribute("hall", hall);
        model.addAttribute("head", "Изменить");
        return "modify_form_hall";
    }

    @PostMapping("/edit/halls/save")
    public String saveHall(@ModelAttribute Hall hall) {
        hallService.saveHall(hall);
        //exh id may be null
        return "redirect:/museum/halls?exh_id=" + hall.getExhibit().getId().toString();
    }

    @GetMapping("/edit/halls/delete")
    public String deleteHall(@RequestParam Long hall_id, Model model){
        //may be null
        Long exh_id = hallService.findHallById(hall_id).getExhibit().getId();
        hallService.deleteHallById(hall_id);
        return "redirect:/museum/halls?exh_id=" + exh_id.toString();
    }
}
