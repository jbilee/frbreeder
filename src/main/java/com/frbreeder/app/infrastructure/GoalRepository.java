package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.Goal;
import org.springframework.data.repository.ListCrudRepository;

public interface GoalRepository extends ListCrudRepository<Goal, Long> {
}
