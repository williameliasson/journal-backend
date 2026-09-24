package com.williameliasson.journal_backend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.williameliasson.journal_backend.models.User;


public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findByUsername(String username);
}
