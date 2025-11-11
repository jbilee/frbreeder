package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.PrimaryGene;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryGeneRepository extends ListCrudRepository<PrimaryGene, Integer> {
}
