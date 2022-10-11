package de.techtask.saucelabs.repository;

import de.techtask.saucelabs.model.AppHealth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppStateRepo extends MongoRepository<AppHealth, Long> {
    List<AppHealth> findAllByProcessedOrderByCreatedAsc(boolean processed);
}
