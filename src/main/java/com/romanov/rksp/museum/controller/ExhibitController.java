package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.service.ExhibitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExhibitController {
    private final ExhibitService exhibitService;
}
