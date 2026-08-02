package com.frbreeder.app.ui.dto;

public record BreedingProject(
        long id,
        Long frId,
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
