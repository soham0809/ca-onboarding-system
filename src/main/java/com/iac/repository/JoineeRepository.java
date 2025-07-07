package com.iac.repository;

import com.iac.model.Joinee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoineeRepository extends MongoRepository<Joinee, String> {
    // Custom query methods can be added here if needed
}