package com.project.planner.repository;

import com.project.planner.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByLoginIgnoreCase(String login);

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByLoginIgnoreCase(String login);

    boolean existsByEmailIgnoreCase(String email);

    Optional<List<User>> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);
}
