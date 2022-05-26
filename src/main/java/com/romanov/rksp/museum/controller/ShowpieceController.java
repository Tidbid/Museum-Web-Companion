package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.service.ShowpieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/museum")
public class ShowpieceController {
    private final ShowpieceService showpieceService;
    //@GetMapping("/edit/showpiece/create")
    //public String showNewExhibitForm(Model model) {
    //    model.addAttribute("exhibit", new Exhibit());
    //    model.addAttribute("head", "Создать");
    //    return "modify_form_exh";
    //}
//
    //@GetMapping("/edit/showpiece/update")
    //public String updateExhibit(@RequestParam Long exh_id, Model model) {
    //    Exhibit exhibit = exhibitService.findExhibitById(exh_id);
    //    model.addAttribute("exhibit", exhibit);
    //    model.addAttribute("head", "Изменить");
    //    return "modify_form_exh";
    //}
//
    //@PostMapping("/edit/showpiece/save")
    //public String saveExhibit(
    //        @ModelAttribute Exhibit exhibit,
    //        @RequestParam("image") MultipartFile multipartFile
    //) {
    //    exhibitService.saveExhibit(exhibit);
    //    //add check for file type
    //    //check for applicability to update requests???
    //    if (exhibit.getImageUrl() == null || exhibit.getImageUrl().isEmpty()) {
    //        String imgUrl = imageService.saveExhibitionImage(exhibit.getId(), multipartFile);
    //        exhibit.setImageUrl(imgUrl);
    //        exhibitService.saveExhibit(exhibit);
    //    }
    //    return "redirect:/exhibitions";
    //}
//
    //@GetMapping("/edit/showpiece/delete")
    //public String deleteExhibit(@RequestParam Long exh_id, Model model){
    //    exhibitService.deleteExhibitById(exh_id);
    //    return "redirect:/exhibitions";
    //}
}
