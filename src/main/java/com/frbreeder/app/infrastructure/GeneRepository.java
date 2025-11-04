package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Gene;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneRepository extends ListCrudRepository<Gene, Integer> {
}
