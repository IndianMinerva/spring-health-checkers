package de.techtask.saucelabs.repository;

import de.techtask.saucelabs.model.AggregatedAppHealth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatedAppHealthRepo extends MongoRepository<AggregatedAppHealth, Long> { }
