package com.frbreeder.app.ui;

import com.frbreeder.app.domain.DragonService;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.ui.dto.NewDragonRequest;
import com.frbreeder.app.ui.dto.RegisteredDragon;
import com.frbreeder.app.ui.dto.RosterDragons;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dragons")
public class DragonController {

    private final DragonService dragonService;

    public DragonController(final DragonService dragonService) {
        this.dragonService = dragonService;
    }

    @GetMapping
    public ResponseEntity<RosterDragons> getAll(final Workspace workspace) {
        return ResponseEntity.ok(dragonService.getDragons(workspace.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisteredDragon> getOne(@PathVariable("id") final Long id, final Workspace workspace) {
        return ResponseEntity.ok(dragonService.getDragon(id, workspace.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable("id") final Long id, final Workspace workspace) {
        dragonService.deleteDragon(id, workspace.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<RosterDragons> addNew(@RequestBody final NewDragonRequest request, final Workspace workspace) {
        RosterDragons added = dragonService.addDragons(workspace, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

}
