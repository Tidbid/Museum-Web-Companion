package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.service.ShowpieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShowpieceController {
    private final ShowpieceService showpieceService;

}
