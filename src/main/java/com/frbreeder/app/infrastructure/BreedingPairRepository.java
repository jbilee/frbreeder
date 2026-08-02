package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.BreedingPair;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedingPairRepository extends ListCrudRepository<BreedingPair, Long> {

    List<BreedingPair> findAllByWorkspaceId(final Long workspaceId);

    List<BreedingPair> findByMaleIdAndWorkspaceId(final Long maleId, final Long workspaceId);

    List<BreedingPair> findByFemaleIdAndWorkspaceId(final Long femaleId, final Long workspaceId);

    boolean existsByMaleIdAndWorkspaceId(final Long maleId, final Long workspaceId);

    boolean existsByFemaleIdAndWorkspaceId(final Long femaleId, final Long workspaceId);

    void deleteByIdAndWorkspaceId(final Long id, final Long workspaceId);

}
