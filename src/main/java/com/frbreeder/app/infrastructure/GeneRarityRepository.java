package com.frbreeder.app.infrastructure;

import com.frbreeder.app.domain.entity.GeneRarity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneRarityRepository extends ListCrudRepository<GeneRarity, String> {

    @Query("""
            SELECT r FROM GeneRarity r
            WHERE r.name = :name
            """)
    Optional<GeneRarity> findByName(@Param("name") final String name);

}
