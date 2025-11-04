package com.frbreeder.app.ui;

import com.frbreeder.app.domain.BreedingService;
import com.frbreeder.app.ui.dto.BreedingResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/breeding")
public class BreedingController {

    private final BreedingService breedingService;

    public BreedingController(final BreedingService breedingService) {
        this.breedingService = breedingService;
    }

    @GetMapping
    ResponseEntity<BreedingResult> getBreedingResult(
            @RequestParam(value = "a") final Long parentAId,
            @RequestParam(value = "b") final Long parentBId
    ) {
        return ResponseEntity.ok(breedingService.getResult(parentAId, parentBId));
    }

}
