package com.williameliasson.journal_backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.williameliasson.journal_backend.models.Mood;

public interface MoodRepository extends CrudRepository<Mood, Integer>{
}
