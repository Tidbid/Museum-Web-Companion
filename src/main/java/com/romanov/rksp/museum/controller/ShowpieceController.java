package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;
import com.romanov.rksp.museum.service.ImageService;
import com.romanov.rksp.museum.service.ShowpieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
@RequestMapping("/museum")
public class ShowpieceController {
    private final ShowpieceService showpieceService;
    private final ImageService imageService;

    @GetMapping("/browse/showpieces/more")
    public String viewShowpieceMore(@RequestParam Long shwp_id, Model model) {
        model.addAttribute("showpiece", showpieceService.findShowpieceById(shwp_id));
        return "showpiece_more";
    }

    @GetMapping("/edit/showpieces/orphans")
    public String viewOrphanShowpieces(Model model) {
        //Using the same view that
        //displays showpieces of a hall
        //but here we display showpieces with
        //no hall assigned
        Hall stub = new Hall("Свободные Экспонаты:", showpieceService.findVacantShowpieces());
        model.addAttribute("hall", stub);
        return "showpieces_edit";
    }

    @GetMapping("/edit/showpieces/all")
    public String viewAllShowpiecesInEditMode(Model model) {
        Hall stub = new Hall("Все Экспонаты:", showpieceService.findAllShowpieces());
        model.addAttribute("hall", stub);
        return "showpieces_edit";
    }

    @GetMapping("/edit/showpieces/create")
    public String showNewShowpieceForm(Model model) {
        model.addAttribute("showpiece", new Showpiece());
        model.addAttribute("head", "Создать");
        return "modify_form_shwp";
    }

    @GetMapping("/edit/showpieces/update")
    public String updateShowpiece(@RequestParam Long shwp_id, Model model) {
        Showpiece showpiece = showpieceService.findShowpieceById(shwp_id);
        model.addAttribute("showpiece", showpiece);
        model.addAttribute("head", "Изменить");
        return "modify_form_shwp";
    }

    //TODO check for any null fields if updated
    @PostMapping("/edit/showpieces/save")
    public String saveShowpiece(
            @ModelAttribute("showpiece") Showpiece showpiece,
            @RequestParam("image") MultipartFile multipartFile
    ) {
        showpieceService.saveShowpiece(showpiece);
        String updatedUrl = imageService.saveShowpieceImage(showpiece, multipartFile);
        if (!showpiece.getImageUrl().equals(updatedUrl))
            showpieceService.updateImageById(showpiece.getId(), updatedUrl);
        return "redirect:/museum/browse/showpieces/more?shwp_id=" + showpiece.getId().toString();
    }

    @GetMapping("/edit/showpieces/delete")
    public String deleteShowpiece(@RequestParam Long shwp_id){
        Long hall_id = showpieceService.deleteShowpieceById(shwp_id);
        String ret = "redirect:/museum/browse/halls/showpieces";
        ret += (hall_id == null) ?
                "" : "?hall_id=" + hall_id;
        //TODO delete images
        return ret;
    }

    //TODO add this to edit html as a button
    @GetMapping("/edit/showpieces/orphanize")
    public ResponseEntity<?> orphanizeShowpiece(@RequestParam Long shwp_id){
          showpieceService.makeOrphan(shwp_id);
          return ResponseEntity.ok().build();
    }
}
