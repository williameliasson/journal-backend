package com.williameliasson.journal_backend.services;

import com.williameliasson.journal_backend.dtos.CreateMoodRequest;
import com.williameliasson.journal_backend.models.Mood;
import com.williameliasson.journal_backend.repositories.MoodRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class MoodService {
    //c
    private final MoodRepository moodRepository;

    MoodService(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }
    
    public void createMood(CreateMoodRequest createMoodRequest){
        String uppercaseName = createMoodRequest.name().toUpperCase(); // all Moods names should be uppercase.
        // check theres no name collision
        if (moodNameExists(uppercaseName)) throw new IllegalArgumentException("Mood name already exists");
        
        // create object
        Mood mood = new Mood(uppercaseName);
        moodRepository.save(mood);
    }

    // Helper method to enforce uniqueness of mood names
    private boolean moodNameExists(String name){
        return getAllMoods().stream()
        .filter(m -> 
            m.getName().equals(name)
        ).findFirst()
        .isPresent();
    }

    //r
    public List<Mood> getAllMoods(){
        return (List<Mood>) moodRepository.findAll();
    }

    public Mood getMoodById(Integer id){
        Optional<Mood> maybeMood = moodRepository.findById(id);
        if (!maybeMood.isPresent()){
            throw new IllegalArgumentException("Mood not found with id");
        }
        return maybeMood.get();
    }

    //u
    //d
}
