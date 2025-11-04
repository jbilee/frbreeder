package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Breed;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository extends ListCrudRepository<Breed, Integer> {
}
