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
import java.util.List;

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
        model.addAttribute("exhibitHallsDto", new ExhibitHallsDto(new Exhibit(), new ArrayList<>()));
        model.addAttribute("vacantHalls", hallService.findVacantHalls());
        model.addAttribute("head", "Создать");
        return "modify_form_exh";
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

    @PostMapping("/edit/exhibitions/save")
    public String saveExhibit(
            @ModelAttribute("exhibitHallsDto") ExhibitHallsDto exhibitHallsDto,
            @RequestParam("image") MultipartFile multipartFile
    ) {
        Exhibit exhibit = exhibitHallsDto.getExhibit();
        //Exhibit oldExhibit = exhibitService.findExhibitById(exhibit.getId());
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
}
