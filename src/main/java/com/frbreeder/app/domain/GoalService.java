package com.frbreeder.app.domain;

import com.frbreeder.app.domain.entity.Goal;
import com.frbreeder.app.infrastructure.GoalRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(final GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> getGoals() {
        return goalRepository.findAll();
    }

    public Goal addGoal(final String scryUrl) {
        Goal goal = parseScryUrl(scryUrl);
        return goalRepository.save(goal);
    }

    private Goal parseScryUrl(final String url) {
        String queryParams = url.substring(url.indexOf("?") + 1);
        String[] queryPairs = queryParams.split("&");
        Map<String, String> queries = new HashMap<>();

        for (final String pair : queryPairs) {
            String[] keyValue = pair.split("=");
            queries.put(keyValue[0], keyValue[1]);
        }

        return new Goal(
                queries.get("breed"),
                queries.get("gender"),
                queries.get("bodygene"),
                queries.get("winggene"),
                queries.get("tertgene"),
                queries.get("body"),
                queries.get("wings"),
                queries.get("tert"),
                queries.get("eyetype"),
                queries.get("element")
        );
    }

}
