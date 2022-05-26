package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.dto.ExhibitHallsDto;
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

    @PostMapping("/edit/test/save")
    public String assignHalls(@ModelAttribute("exhibitHallsDto") ExhibitHallsDto exhibitHallsDto) {
        exhibitService.addHalls(exhibitHallsDto.getExhibit(), exhibitHallsDto.getHallsToAdd());
        hallService.assignHalls(exhibitHallsDto.getExhibit(), exhibitHallsDto.getHallsToAdd());
        return "redirect:/museum/browse/exhibitions/halls?exh_id=" + exhibitHallsDto.getExhibit().getId().toString();
    }

    @GetMapping
    public String viewIndexPage(Model model) {return "index";}

    @GetMapping("/exhibitions")
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

    @GetMapping("/exhibitions/halls/showpieces/more")
    public String viewShowpieceMore(@RequestParam Long showp_id, Model model) {
        model.addAttribute("showpiece", showpieceService.findShowpieceById(showp_id));
        return "showpiece_more";
    }

    @GetMapping("/edit/exhibitions/create")
    public String showNewExhibitForm(Model model) {
        model.addAttribute("exhibit", new Exhibit());
        model.addAttribute("head", "Создать");
        return "modify_form_exh";
    }

    @GetMapping("/edit/exhibitions/update")
    public String updateExhibit(@RequestParam Long exh_id, Model model) {
        Exhibit exhibit = exhibitService.findExhibitById(exh_id);
        model.addAttribute("exhibit", exhibit);
        model.addAttribute("head", "Изменить");
        return "modify_form_exh";
    }

    @PostMapping("/edit/exhibitions/save")
    public String saveExhibit(
            @ModelAttribute Exhibit exhibit,
            @RequestParam("image") MultipartFile multipartFile
            ) {
        exhibitService.saveExhibit(exhibit);
        //add check for file type
        //check for applicability to update requests???
        if (exhibit.getImageUrl() == null || exhibit.getImageUrl().isEmpty()) {
            String imgUrl = imageService.saveExhibitionImage(exhibit.getId(), multipartFile);
            exhibit.setImageUrl(imgUrl);
            exhibitService.saveExhibit(exhibit);
        }
        return "redirect:/exhibitions";
    }

    @GetMapping("/edit/exhibitions/delete")
    public String deleteExhibit(@RequestParam Long exh_id, Model model){
        exhibitService.deleteExhibitById(exh_id);
        return "redirect:/exhibitions";
    }
}