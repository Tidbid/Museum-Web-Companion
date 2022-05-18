package com.romanov.rksp.museum.controller;

import com.romanov.rksp.museum.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;

}
