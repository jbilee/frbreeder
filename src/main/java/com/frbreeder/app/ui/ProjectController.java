package com.frbreeder.app.ui;

import com.frbreeder.app.domain.ProjectService;
import com.frbreeder.app.ui.dto.BreedingProject;
import com.frbreeder.app.ui.dto.BreedingProjects;
import com.frbreeder.app.ui.dto.NewProjectRequest;
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
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<BreedingProjects> getAll() {
        return ResponseEntity.ok(projectService.getProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreedingProject> getOne(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @PostMapping
    public ResponseEntity<BreedingProject> addNew(@RequestBody final NewProjectRequest request,
                                                  final UriComponentsBuilder ucb) {
        BreedingProject added = projectService.addProject(request);
        URI uri = ucb.path("projects/{id}").buildAndExpand(added.id()).toUri();
        return ResponseEntity.created(uri).body(added);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable("id") final Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

}
