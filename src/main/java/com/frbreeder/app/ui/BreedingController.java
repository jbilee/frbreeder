package com.frbreeder.app.ui;

import com.frbreeder.app.domain.BreedingService;
import com.frbreeder.app.domain.entity.Workspace;
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
    ResponseEntity<Void> createPair(@RequestBody final NewPairRequest request, final Workspace workspace) {
        breedingService.addBreedingPair(request, workspace);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pairs")
    ResponseEntity<List<DragonPair>> getAllPairs(final Workspace workspace) {
        return ResponseEntity.ok(breedingService.getDragonPairs(workspace.getId()));
    }

    @DeleteMapping("/pairs/{id}")
    ResponseEntity<Void> deletePair(@PathVariable("id") final Long id, final Workspace workspace) {
        breedingService.deletePair(id, workspace.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<BreedingResult> getBreedingResult(
            @RequestParam(value = "a") final Long parentAId,
            @RequestParam(value = "b") final Long parentBId
    ) {
        return ResponseEntity.ok(breedingService.getResult(parentAId, parentBId));
    }

}
