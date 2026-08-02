package com.frbreeder.app.domain;

import com.frbreeder.app.common.error.InvalidRequestException;
import com.frbreeder.app.common.error.NotFoundException;
import com.frbreeder.app.domain.common.FrColor;
import com.frbreeder.app.domain.entity.Breed;
import com.frbreeder.app.domain.entity.Project;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.infrastructure.BreedRepository;
import com.frbreeder.app.infrastructure.PrimaryGeneRepository;
import com.frbreeder.app.infrastructure.ProjectRepository;
import com.frbreeder.app.infrastructure.SecondaryGeneRepository;
import com.frbreeder.app.infrastructure.TertiaryGeneRepository;
import com.frbreeder.app.ui.dto.BreedingProject;
import com.frbreeder.app.ui.dto.BreedingProjects;
import com.frbreeder.app.ui.dto.NewProjectRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BreedRepository breedRepository;
    private final PrimaryGeneRepository primaryGeneRepository;
    private final SecondaryGeneRepository secondaryGeneRepository;
    private final TertiaryGeneRepository tertiaryGeneRepository;

    public ProjectService(final ProjectRepository projectRepository, final BreedRepository breedRepository,
                          final PrimaryGeneRepository primaryGeneRepository, final SecondaryGeneRepository secondaryGeneRepository,
                          final TertiaryGeneRepository tertiaryGeneRepository
    ) {
        this.projectRepository = projectRepository;
        this.breedRepository = breedRepository;
        this.primaryGeneRepository = primaryGeneRepository;
        this.secondaryGeneRepository = secondaryGeneRepository;
        this.tertiaryGeneRepository = tertiaryGeneRepository;
    }

    public BreedingProjects getProjects(final Long workspaceId) {
        List<Project> projects = projectRepository.findAllByWorkspaceId(workspaceId);
        return new BreedingProjects(projects
                .stream()
                .map(this::getBreedingProject)
                .toList()
        );
    }

    @Transactional
    public BreedingProject addProject(final NewProjectRequest request, final Workspace workspace) {
        Project project = parseScryUrl(request.frId(), request.name(), request.scryUrl(), workspace);
        Project newProject = projectRepository.save(project);
        return getBreedingProject(newProject);
    }

    private BreedingProject getBreedingProject(final Project project) {
        return new BreedingProject(
                project.getId(),
                project.getFrId(),
                project.getName(),
                project.getBreed().getName(),
                project.getPrimaryGene(),
                project.getSecondaryGene(),
                project.getTertiaryGene(),
                FrColor.findByFrId(project.getPrimaryColorId()).getName(),
                FrColor.findByFrId(project.getSecondaryColorId()).getName(),
                FrColor.findByFrId(project.getTertiaryColorId()).getName()
        );
    }

    private Project parseScryUrl(final Long frId, final String name, final String url, final Workspace workspace) {
        String queryParams = url.substring(url.indexOf("?") + 1);
        String[] queryPairs = queryParams.split("&");
        Map<String, Integer> queries = new HashMap<>();

        for (String pair : queryPairs) {
            String[] keyValue = pair.split("=");
            int value = Integer.parseInt(keyValue[1]);
            queries.put(keyValue[0], value);
        }

        return new Project(
                frId,
                name,
                url,
                getBreedFromScry(queries.get("breed")),
                queries.get("gender"),
                getPrimaryGeneFromScry(queries.get("bodygene")),
                getSecondaryGeneFromScry(queries.get("winggene")),
                getTertiaryGeneFromScry(queries.get("tertgene")),
                queries.get("body"),
                queries.get("wings"),
                queries.get("tert"),
                queries.get("element").toString(),
                workspace
        );
    }

    private Breed getBreedFromScry(final int breedId) {
        return breedRepository.findById(breedId)
                .orElseThrow();
    }

    private String getPrimaryGeneFromScry(final int geneId) {
        return primaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This primary gene is not in the database."))
                .getName();
    }

    private String getSecondaryGeneFromScry(final int geneId) {
        return secondaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This secondary gene is not in the database."))
                .getName();
    }

    private String getTertiaryGeneFromScry(final int geneId) {
        return tertiaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This tertiary gene is not in the database."))
                .getName();
    }

    @Transactional
    public void deleteProject(final Long id, final Long workspaceId) {
        projectRepository.deleteByIdAndWorkspaceId(id, workspaceId);
    }

    public BreedingProject getProject(final Long workspaceId, final Long id) {
        Project project = projectRepository.findByIdAndWorkspaceId(id, workspaceId)
                .orElseThrow(() -> new InvalidRequestException("The project by that id does not exist."));
        return getBreedingProject(project);
    }

}
