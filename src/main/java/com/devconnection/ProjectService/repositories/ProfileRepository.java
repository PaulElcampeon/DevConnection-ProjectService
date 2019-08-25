package com.devconnection.ProjectService.repositories;

import com.devconnection.ProjectService.domain.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
}
