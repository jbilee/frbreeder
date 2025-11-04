package com.frbreeder.app.domain;

import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.infrastructure.DragonRepository;
import com.frbreeder.app.ui.dto.BreedingResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BreedingService {

    private final DragonRepository dragonRepository;

    public BreedingService(final DragonRepository dragonRepository) {
        this.dragonRepository = dragonRepository;
    }

    public BreedingResult getResult(final long parentAId, final long parentBId) {
        if (parentAId == parentBId) {
            throw new IllegalArgumentException("Can't be identical.");
        }

        Dragon parentA = dragonRepository.findById(parentAId).orElseThrow();
        Dragon parentB = dragonRepository.findById(parentBId).orElseThrow();

        if (parentA.getGender().equals(parentB.getGender())) {
            throw new IllegalArgumentException("Can't be of the same gender.");
        }

        // TODO: Validate lineage

        // TODO: Calculate breed probability
        // TODO: Calculate gene probabilities
        // TODO: Find color range
        return new BreedingResult(
                List.of(),
                List.of(),
                null,
                List.of(),
                null,
                List.of(),
                null
        );
    }

}
