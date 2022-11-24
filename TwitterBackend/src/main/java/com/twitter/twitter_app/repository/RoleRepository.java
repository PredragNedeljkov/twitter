package com.twitter.twitter_app.repository;

import java.util.Optional;

import com.twitter.twitter_app.models.ERole;
import com.twitter.twitter_app.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
