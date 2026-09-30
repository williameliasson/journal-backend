package com.williameliasson.journal_backend.configs;

import com.williameliasson.journal_backend.models.Mood;
import com.williameliasson.journal_backend.repositories.MoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// src: https://www.geeksforgeeks.org/advance-java/spring-boot-load-initial-data/
@Component 
public class DataLoader implements CommandLineRunner{


    private final MoodRepository moodRepository;

    DataLoader(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (moodRepository.count() == 0){
            moodRepository.save(new Mood("Happy", "😁"));
            moodRepository.save(new Mood("Sad", "😢"));
            moodRepository.save(new Mood("Anxious", "😰"));
            moodRepository.save(new Mood("Angry", "😡"));
            moodRepository.save(new Mood("Excited", "🤩"));
            moodRepository.save(new Mood("Calm", "😌"));
            moodRepository.save(new Mood("Confused", "😕"));
            moodRepository.save(new Mood("Tired", "😴"));
        }
    }
    
}
