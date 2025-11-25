package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Project;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {

    List<Project> findAllByWorkspaceId(Long workspaceId);

    void deleteByIdAndWorkspaceId(Long id, Long workspaceId);

}
