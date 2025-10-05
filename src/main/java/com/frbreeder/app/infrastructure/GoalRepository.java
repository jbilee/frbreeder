package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Goal;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends ListCrudRepository<Goal, Long> {
}
