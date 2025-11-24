package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Workspace;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends ListCrudRepository<Workspace, Long> {

    Optional<Workspace> findByNameAndPassword(final String name, final String password);

    boolean existsByName(final String name);

    @Modifying
    @NativeQuery("""
            UPDATE workspaces w
            SET last_logged_in = NOW()
            WHERE id = :workspaceId
            """)
    void updatedLastLoggedIn(@Param("workspaceId") final Long workspaceId);

}
