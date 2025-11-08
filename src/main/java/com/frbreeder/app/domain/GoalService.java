package com.frbreeder.app.domain;

import com.frbreeder.app.domain.entity.Breed;
import com.frbreeder.app.domain.entity.Gene;
import com.frbreeder.app.domain.entity.Goal;
import com.frbreeder.app.infrastructure.BreedRepository;
import com.frbreeder.app.infrastructure.GeneRepository;
import com.frbreeder.app.infrastructure.GoalRepository;
import com.frbreeder.app.ui.dto.BreedingGoal;
import com.frbreeder.app.ui.dto.BreedingGoals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final BreedRepository breedRepository;
    private final GeneRepository geneRepository;

    public GoalService(final GoalRepository goalRepository, final BreedRepository breedRepository, final GeneRepository geneRepository) {
        this.goalRepository = goalRepository;
        this.breedRepository = breedRepository;
        this.geneRepository = geneRepository;
    }

    public BreedingGoals getGoals() {
        List<Goal> goals = goalRepository.findAll();
        return new BreedingGoals(
                goals.stream()
                        .map(this::getBreedingGoal)
                        .toList()
        );
    }

    public BreedingGoal addGoal(final String scryUrl) {
        Goal goal = parseScryUrl(scryUrl);
        Goal newGoal = goalRepository.save(goal);
        return getBreedingGoal(newGoal);
    }

    private BreedingGoal getBreedingGoal(final Goal goal) {
        return new BreedingGoal(
                goal.getId(),
                goal.getBreed().getName(),
                goal.getPrimaryGene().getName(),
                goal.getSecondaryGene().getName(),
                goal.getTertiaryGene().getName(),
                FrColor.findByFrId(goal.getPrimaryColorId()).getName(),
                FrColor.findByFrId(goal.getSecondaryColorId()).getName(),
                FrColor.findByFrId(goal.getTertiaryColorId()).getName()
        );
    }

    private Goal parseScryUrl(final String url) {
        String queryParams = url.substring(url.indexOf("?") + 1);
        String[] queryPairs = queryParams.split("&");
        Map<String, Integer> queries = new HashMap<>();

        for (final String pair : queryPairs) {
            String[] keyValue = pair.split("=");
            int value = Integer.parseInt(keyValue[1]);
            queries.put(keyValue[0], value);
        }

        return new Goal(
                getBreedFromScry(queries.get("breed")),
                queries.get("gender"),
                getGeneFromScry(queries.get("bodygene")),
                getGeneFromScry(queries.get("winggene")),
                getGeneFromScry(queries.get("tertgene")),
                queries.get("body"),
                queries.get("wings"),
                queries.get("tert"),
                queries.get("element").toString()
        );
    }

    private Breed getBreedFromScry(final int breedId) {
        return breedRepository.findById(breedId)
                .orElseThrow();
    }

    private Gene getGeneFromScry(final int geneId) {
        return geneRepository.findById(geneId)
                .orElseThrow();
    }

}
