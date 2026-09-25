package com.williameliasson.journal_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// For storing 'categories' for the JournalEntries. Where name field can be 'HAPPY', 'SAD', etc.
// All users share the same set of available Moods to choose from

@Entity 
@Table (name = "moods")
@Getter 
@Setter 
@NoArgsConstructor 
public class Mood {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false, unique = true)
    private String name;

    public Mood(String name){
        this.name = name;
    }
}
