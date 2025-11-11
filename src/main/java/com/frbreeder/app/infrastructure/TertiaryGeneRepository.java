package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.TertiaryGene;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TertiaryGeneRepository extends ListCrudRepository<TertiaryGene, Integer> {
}
