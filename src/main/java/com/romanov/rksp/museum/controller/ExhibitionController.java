package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.dto.ExhibitHallsDto;
import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.service.ExhibitService;
import com.romanov.rksp.museum.service.HallService;
import com.romanov.rksp.museum.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/museum")
public class ExhibitionController {
    private final ExhibitService exhibitService;
    private final HallService hallService;
    private final ImageService imageService;

    @GetMapping("/browse/exhibitions")
    public String viewExhibitions(Model model) {
        List<Exhibit> exhibitionsList = this.exhibitService.findAllExhibits();
        model.addAttribute("exhibitions", exhibitionsList);
        return "all_exh";
    }

    //TODO add a view that presents
    // elaborate information about an
    // exhibition (from which we can get to halls)

    @GetMapping("/browse/exhibitions/halls")
    public String viewHalls(@RequestParam Long exh_id, Model model) {
        model.addAttribute("exhibit", exhibitService.findExhibitById(exh_id));
        return "halls";
    }

    @GetMapping("/edit/exhibitions/create")
    public String showNewExhibitForm(Model model) {
        Exhibit exhibit = new Exhibit();
        exhibit.setHalls(new ArrayList<>());
        model.addAttribute("exhibitHallsDto", new ExhibitHallsDto(exhibit, new ArrayList<>()));
        model.addAttribute("vacantHalls", hallService.findVacantHalls());
        model.addAttribute("head", "Создать");
            return "modify_form_exh";
    }

    @GetMapping("/edit/exhibitions/update")
    public String updateExhibit(@RequestParam Long exh_id, Model model) {
        Exhibit exhibit = exhibitService.findExhibitById(exh_id);
        model.addAttribute("exhibitHallsDto",
                new ExhibitHallsDto(exhibit, exhibit.getHalls()));
        model.addAttribute("vacantHalls", hallService.findVacantHalls());
        model.addAttribute("head", "Изменить");
        return "modify_form_exh";
    }

    @PostMapping("/edit/exhibitions/save")
    public String saveExhibit(
            @ModelAttribute("exhibitHallsDto") ExhibitHallsDto exhibitHallsDto,
            @RequestParam("image") MultipartFile multipartFile
    ) {
        Exhibit exhibit = exhibitHallsDto.getExhibit();
        ArrayList<Set<Long>> halls =
                exhibitService.saveExhibitAndProcessHalls(exhibit, exhibitHallsDto.getHallsToAdd());
        hallService.makeOrphan(halls.get(0));
        hallService.assignExhibit(exhibit.getId(), halls.get(1));
        String imgUrl = imageService.saveExhibitionImage(exhibit, multipartFile);
        exhibitService.updateImageById(exhibit.getId(), imgUrl);
        return "redirect:/museum/browse/exhibitions/halls?exh_id=" + exhibit.getId().toString();
    }

    @GetMapping("/edit/exhibitions/delete")
    public String deleteExhibit(@RequestParam Long exh_id, Model model){
        //TODO can be made more efficient by
        // ADD CONSTRAINT ON DELETE SET NULL
        // in the database
        List<Hall> halls = (List<Hall>) exhibitService.findExhibitById(exh_id).getHalls();
        Set<Long> orphans = new LinkedHashSet<>(halls.size());
        for (Hall hall : halls)
            orphans.add(hall.getId());
        hallService.makeOrphan(orphans);
        //TODO add modelparam that will allow user to choose to delete halls!!!
        //TODO add image deletion, since they will clog the disk
        exhibitService.deleteExhibitById(exh_id);
        return "redirect:/museum/browse/exhibitions";
    }
}
