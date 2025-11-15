package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Dragon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DragonRepository extends ListCrudRepository<Dragon, Long> {

    @EntityGraph(attributePaths = {
            "breed",
            "primaryGene",
            "secondaryGene",
            "tertiaryGene",
            "workspace"
    })
    List<Dragon> findAllByWorkspaceId(final Long workspaceId);

    Optional<Dragon> findByIdAndWorkspaceId(final Long id, final Long workspaceId);

    void deleteById(final Long id);

}
