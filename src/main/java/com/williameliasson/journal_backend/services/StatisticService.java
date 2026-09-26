package com.williameliasson.journal_backend.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.williameliasson.journal_backend.dtos.MoodStat;
import com.williameliasson.journal_backend.dtos.StatisticsResponse;
import com.williameliasson.journal_backend.models.JournalEntry;
import com.williameliasson.journal_backend.models.User;

@Service 
public class StatisticService {
    private final JournalEntryService journalEntryService;
    private final AuthService authService;

    StatisticService(AuthService authService, JournalEntryService journalEntryService) {
        this.authService = authService;
        this.journalEntryService = journalEntryService;
    }

    public StatisticsResponse getStatistics(LocalDate from, LocalDate to, String username){
        // for storing the counts of each mood in the retrieved dataset. In the form of key: moodname, value: current count
        HashMap<String, Integer> moodCounts = new HashMap<>();
        
        // get the user in question
        User user = authService.getUserByUsername(username);
        
        // the dataset
        List<JournalEntry> entries = journalEntryService.getJournalEntriesByUserBetweenDates(user, from, to);
        Integer totalCount = entries.size();
        // Populate the moodCounts map
        entries.forEach(entry -> {
            String moodName = entry.getMood().getName();

            // if the key exists, increase its corresponding value by one
            if (moodCounts.containsKey(moodName)){
                moodCounts.put(moodName, moodCounts.get(moodName) + 1); // increase count of moodname by 1
            // otherwise, set it's value to one.
            } else {
                moodCounts.put(moodName, 1);
            }
        });

        List<MoodStat> moodStats = new ArrayList<>();

        // convert each moodCount into percentages
        moodCounts.forEach((name, count ) -> {
            moodStats.add( new MoodStat(name, (double) count/totalCount));
        });

        StatisticsResponse statisticsResponse = new StatisticsResponse(username, moodStats, totalCount);
        return statisticsResponse;
    }
}
