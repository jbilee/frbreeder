package com.frbreeder.app.ui.dto;

public record RosterDragon(
        long id,
        Long frId,
        String name,
        String breed,
        String gender,
        String primaryGene,
        String secondaryGene,
        String tertiaryGene,
        String primaryColor,
        String secondaryColor,
        String tertiaryColor
) {
}
