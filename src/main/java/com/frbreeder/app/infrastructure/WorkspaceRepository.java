package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Workspace;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends ListCrudRepository<Workspace, Long> {

    Optional<Workspace> findByNameAndPassword(final String name, final String password);

    boolean existsByName(final String name);

}
