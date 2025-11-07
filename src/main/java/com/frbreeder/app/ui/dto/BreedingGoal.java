package com.frbreeder.app.ui.dto;

public record BreedingGoal(
        long id,
        String breed,
        String primaryGene,
        String secondaryGene,
        String tertiaryGene,
        String primaryColor,
        String secondaryColor,
        String tertiaryColor
) {
}
