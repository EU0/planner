package com.project.planner.repository;

import com.project.planner.domain.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Optional<Authority> findByName(String name);
}
