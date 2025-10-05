package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Dragon;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DragonRepository extends ListCrudRepository<Dragon, Long> {
}
