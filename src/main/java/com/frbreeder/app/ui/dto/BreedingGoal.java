package com.frbreeder.app.ui.dto;

public record BreedingGoal(
        long id,
        String name,
        String breed,
        String primaryGene,
        String secondaryGene,
        String tertiaryGene,
        String primaryColor,
        String secondaryColor,
        String tertiaryColor
) {
}
