package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.BreedingPair;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedingPairRepository extends ListCrudRepository<BreedingPair, Long> {

    List<BreedingPair> findAllByWorkspaceId(final Long workspaceId);

}
