package com.romanov.rksp.museum.dto;

import com.romanov.rksp.museum.model.Exhibit;
import com.romanov.rksp.museum.model.Hall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitHallsDto {
    private Exhibit exhibit;
    private List<Hall> hallsToAdd;
}
