package com.romanov.rksp.museum.dto;

import com.romanov.rksp.museum.model.Hall;
import com.romanov.rksp.museum.model.Showpiece;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallShowpiecesDto {
    private Hall hall;
    private List<Showpiece> showpiecesToAdd;
}
