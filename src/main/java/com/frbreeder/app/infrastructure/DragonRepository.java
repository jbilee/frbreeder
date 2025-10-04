package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.Dragon;
import org.springframework.data.repository.ListCrudRepository;

public interface DragonRepository extends ListCrudRepository<Dragon, Long> {
}
