package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.SecondaryGene;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondaryGeneRepository extends ListCrudRepository<SecondaryGene, Integer> {
}
