package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {

    List<Project> findAll();

    Optional<Project> findById(final Long id);

    void deleteById(final Long id);

}
