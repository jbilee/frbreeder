package com.frbreeder.app.ui;

import com.frbreeder.app.domain.GoalService;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.ui.dto.BreedingGoal;
import com.frbreeder.app.ui.dto.BreedingGoals;
import com.frbreeder.app.ui.dto.NewGoalRequest;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(final GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<BreedingGoals> getAll() {
        return ResponseEntity.ok(goalService.getGoals());
    }

    @PostMapping
    public ResponseEntity<BreedingGoal> addNew(@RequestBody final NewGoalRequest newGoal, final Workspace workspace, final UriComponentsBuilder ucb) {
        BreedingGoal added = goalService.addGoal(newGoal, workspace);
        URI uri = ucb.path("goals/{id}").buildAndExpand(added.id()).toUri();
        return ResponseEntity.created(uri).body(added);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable("id") final Long id, final Workspace workspace) {
        goalService.deleteGoal(id, workspace.getId());
        return ResponseEntity.ok().build();
    }

}
