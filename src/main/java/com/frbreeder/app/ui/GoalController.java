package com.frbreeder.app.ui;

import com.frbreeder.app.domain.Goal;
import com.frbreeder.app.domain.GoalService;
import com.frbreeder.app.ui.dto.NewGoalRequest;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(final GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getAll() {
        return ResponseEntity.ok(goalService.getGoals());
    }

    @PostMapping
    public ResponseEntity<Goal> addNew(@RequestBody final NewGoalRequest newGoal, final UriComponentsBuilder ucb) {
        Goal added = goalService.addGoal(newGoal.scryUrl());
        URI uri = ucb.path("goals/{id}").buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).body(added);
    }

}
