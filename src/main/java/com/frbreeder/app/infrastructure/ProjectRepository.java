package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Project;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {

    void deleteByIdAndWorkspaceId(Long id, Long workspaceId);

}
