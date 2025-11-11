package com.frbreeder.app.ui;

import com.frbreeder.app.domain.DragonService;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.ui.dto.NewDragonRequest;
import com.frbreeder.app.ui.dto.RosterDragon;
import com.frbreeder.app.ui.dto.RosterDragons;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/dragons")
public class DragonController {

    private final DragonService dragonService;

    public DragonController(final DragonService dragonService) {
        this.dragonService = dragonService;
    }

    @GetMapping
    public ResponseEntity<RosterDragons> getAll() {
        return ResponseEntity.ok(dragonService.getDragons());
    }

    @PostMapping
    public ResponseEntity<RosterDragon> addNew(@RequestBody final NewDragonRequest newDragon, final Workspace workspace, final UriComponentsBuilder ucb) {
        RosterDragon added = dragonService.addDragon(workspace, newDragon.name(), newDragon.scryUrl());
        URI uri = ucb.path("dragons/{id}").buildAndExpand(added.id()).toUri();
        return ResponseEntity.created(uri).body(added);
    }

}
