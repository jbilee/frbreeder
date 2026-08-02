package com.frbreeder.app.ui;

import com.frbreeder.app.domain.BreedingService;
import com.frbreeder.app.ui.dto.BreedingResult;
import com.frbreeder.app.ui.dto.DragonPair;
import com.frbreeder.app.ui.dto.NewPairRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/breeding")
public class BreedingController {

    private final BreedingService breedingService;

    public BreedingController(final BreedingService breedingService) {
        this.breedingService = breedingService;
    }

    @PostMapping("/pairs")
    ResponseEntity<Void> createPair(@RequestBody final NewPairRequest request) {
        breedingService.addBreedingPair(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pairs/all")
    ResponseEntity<List<DragonPair>> getAllPairs() {
        return ResponseEntity.ok(breedingService.getDragonPairs());
    }

    @GetMapping("/pairs/search")
    ResponseEntity<List<DragonPair>> getPairsByDragonGender(@RequestParam(value = "gender") final String gender,
                                                            @RequestParam(value = "id") final Long id
    ) {
        return ResponseEntity.ok(breedingService.searchPairByGender(gender, id));
    }

    @DeleteMapping("/pairs/{id}")
    ResponseEntity<Void> deletePair(@PathVariable("id") final Long id) {
        breedingService.deletePair(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<BreedingResult> getBreedingResult(@RequestParam(value = "a") final Long parentAId,
                                                     @RequestParam(value = "b") final Long parentBId) {
        return ResponseEntity.ok(breedingService.getResult(parentAId, parentBId));
    }

}
