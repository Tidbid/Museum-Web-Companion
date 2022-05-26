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
@RequestMapping("/museum")
@RequiredArgsConstructor
public class MuseumController {
    private final ExhibitService exhibitService;
    private final HallService hallService;
    private final ShowpieceService showpieceService;

    private final ImageService imageService;

    @GetMapping("/edit/test")
    public String selectHalls(@RequestParam Long exh_id, Model model) {
        Exhibit exh = exhibitService.findExhibitById(exh_id);
        model.addAttribute("exhibitHallsDto", new ExhibitHallsDto(exh, new ArrayList<>()));
        model.addAttribute("vacantHalls", hallService.findVacantHalls());
        return "select_halls_for_exh";
    }

    @GetMapping("/edit/exhibitions/create")
    public String showNewExhibitForm(Model model) {
        model.addAttribute("exhibitHallsDto", new ExhibitHallsDto(new Exhibit(), new ArrayList<>()));
        model.addAttribute("vacantHalls", hallService.findVacantHalls());
        model.addAttribute("head", "Создать");
        return "modify_form_exh";
    }

    @GetMapping("/edit/halls/create")
    public String showNewHallForm(Model model) {
        model.addAttribute("hallShowpiecesDto", new HallShowpiecesDto(new Hall(), new ArrayList<>()));
        model.addAttribute("vacantShowpieces", showpieceService.findVacantShowpieces());
        model.addAttribute("head", "Создать");
        return "modify_form_hall";
    }

    @GetMapping("/edit/exhibitions/update")
    public String updateExhibit(@RequestParam Long exh_id, Model model) {
        Exhibit exhibit = exhibitService.findExhibitById(exh_id);
        model.addAttribute("exhibitHallsDto",
                new ExhibitHallsDto(exhibit, (List<Hall>) exhibit.getHalls()));
        model.addAttribute("vacantHalls", hallService.findVacantHalls());
        model.addAttribute("head", "Изменить");
        return "modify_form_exh";
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
        Hall hall = hallShowpiecesDto.getHall();
        hallService.saveHall(hall);
        showpieceService.assignHall(hall, hallShowpiecesDto.getShowpiecesToAdd());
        return "redirect:/museum/browse/halls/showpieces?hall_id=" + hall.getId().toString();
    }

    @PostMapping("/edit/exhibitions/save")
    public String saveExhibit(
            @ModelAttribute("exhibitHallsDto") ExhibitHallsDto exhibitHallsDto,
            @RequestParam("image") MultipartFile multipartFile
    ) {
        Exhibit exhibit = exhibitHallsDto.getExhibit();
        exhibitService.saveExhibit(exhibit);
        hallService.assignExhibit(exhibit, exhibitHallsDto.getHallsToAdd());
        String imgUrl = imageService.saveExhibitionImage(exhibit, multipartFile);
        exhibit.setImageUrl(imgUrl);
        //TODO look into JPA more closely
        // this entity should be persisted
        // but the session does not flush and
        // changes are not committed without this save
        // (should remove it but don't care to rn)
        exhibitService.saveExhibit(exhibit);
        return "redirect:/museum/browse/exhibitions/halls?exh_id=" + exhibit.getId().toString();
    }

    @GetMapping
    public String viewIndexPage(Model model) {return "index";}

    @GetMapping("/browse/exhibitions")
    public String viewExhibitions(Model model) {
        List<Exhibit> exhibitionsList = this.exhibitService.findAllExhibits();
        model.addAttribute("exhibitions", exhibitionsList);
        return "all_exh";
    }

    @GetMapping("/browse/exhibitions/halls")
    public String viewHalls(@RequestParam Long exh_id, Model model) {
        model.addAttribute("exhibit", exhibitService.findExhibitById(exh_id));
        return "halls";
    }

    @GetMapping("/exhibitions/halls/showpieces")
    public String viewShowpieces(@RequestParam Long hall_id, Model model) {
        model.addAttribute("hall", hallService.findHallById(hall_id));
        return "showpieces";
    }

    @GetMapping("/edit/exhibitions/delete")
    public String deleteExhibit(@RequestParam Long exh_id, Model model){
        //TODO supplant this inefficient garbage with
        // ADD CONSTRAINT ON DELETE SET NULL
        // in the database
        hallService.makeOrphan(exhibitService.findExhibitById(exh_id).getHalls());
        //TODO delete images, since they will clog
        exhibitService.deleteExhibitById(exh_id);
        return "redirect:/exhibitions";
    }

    @GetMapping("/edit/halls/delete")
    public String deleteHall(@RequestParam Long hall_id, Model model){
        Hall hall = hallService.findHallById(hall_id);
        Long exh_id = hall.getId();
        String ret = "redirect:/museum/browse/";
        ret += (exh_id == null) ? "halls/orphans" : "exhibitions/halls?exh_id=" + exh_id;
        //TODO supplant this inefficient garbage with
        // ADD CONSTRAINT ON DELETE SET NULL
        // in the database
        showpieceService.makeOrphan(hall.getShowpieces());
        hallService.deleteHallById(hall_id);
        return ret;
    }
}