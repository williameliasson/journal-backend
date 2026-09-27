package com.williameliasson.journal_backend;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.williameliasson.journal_backend.dtos.StatisticsResponse;
import com.williameliasson.journal_backend.models.JournalEntry;
import com.williameliasson.journal_backend.models.Mood;
import com.williameliasson.journal_backend.models.User;
import com.williameliasson.journal_backend.services.AuthService;
import com.williameliasson.journal_backend.services.JournalEntryService;
import com.williameliasson.journal_backend.services.MoodService;
import com.williameliasson.journal_backend.services.StatisticService;

@ExtendWith (MockitoExtension.class)
public class StatisticServiceTests {
    @Mock 
    MoodService moodService;

    @Mock 
    JournalEntryService journalEntryService;

    @Mock
    AuthService authService;

    @InjectMocks 
    StatisticService statisticService;

    @Test 
    void shouldReturnCorrectStatisticsByUser(){
        LocalDate from = LocalDate.of(2026, 9, 1);
        LocalDate to = LocalDate.of(2026, 9, 10);
        String username = "bob";
        User user = new User();
        user.setUsername(username);
        
        Mood happyMood = new Mood();
        happyMood.setName("happy");
        Mood sadMood = new Mood();
        sadMood.setName("sad");

        // one entry with happy mood
        JournalEntry j1 = new JournalEntry();
        j1.setMood(happyMood);
        j1.setCreatedAt(from.plusDays(1).atStartOfDay(ZoneId.of("Europe/Stockholm")).toInstant());
        
        // one entry with sad mood
        JournalEntry j2 = new JournalEntry();
        j2.setMood(sadMood);
        j2.setCreatedAt(from.plusDays(1).atStartOfDay(ZoneId.of("Europe/Stockholm")).toInstant());

        // mock authService behavior
        when(authService.getUserByUsername(username)).thenReturn(user);

        // mock entryservice behavior
        when(journalEntryService.getJournalEntriesByUserBetweenDates(user, from, to))
            .thenReturn(List.of(j1, j2));

        // mock moodservice behavior
        when(moodService.getAllMoods())
            .thenReturn(List.of(happyMood, sadMood));

        StatisticsResponse statisticsResponse = statisticService.getStatistics(from, to, username);
        statisticsResponse.moodStats().forEach(ms -> {
            System.out.println(ms.moodName());
            System.out.println(ms.percentage());
        });
        // Assert that the first one of the moodstats (one of two) is correctly being set to 50%, which should account for 50% total journalentries.
        assertEquals(0.5, statisticsResponse.moodStats().get(0).percentage());
    }

    @Test 
    void shouldReturnPercentagesAsZeroWhenNoEntries(){
        LocalDate from = LocalDate.of(2026, 9, 1);
        LocalDate to = LocalDate.of(2026, 9, 10);
        String username = "bob";
        User user = new User();
        user.setUsername(username);
        
        Mood happyMood = new Mood();
        happyMood.setName("happy");
        Mood sadMood = new Mood();
        sadMood.setName("sad");

        // no journalEntries

        // mock authService behavior
        when(authService.getUserByUsername(username)).thenReturn(user);

        // mock entryservice behavior
        when(journalEntryService.getJournalEntriesByUserBetweenDates(user, from, to))
            .thenReturn(List.of()); // empty list of journalEntries

        // mock moodservice behavior
        when(moodService.getAllMoods())
            .thenReturn(List.of(happyMood, sadMood));

        StatisticsResponse statisticsResponse = statisticService.getStatistics(from, to, username);
        statisticsResponse.moodStats().forEach(ms -> {
            System.out.println(ms.moodName());
            System.out.println(ms.percentage());
        });
        // Assert that all moodStat shares are 0% as there are no journalEntries within the specified date range
        statisticsResponse.moodStats().forEach(ms -> {
            assertEquals(0, ms.percentage());
        });
        
    }
    
}
