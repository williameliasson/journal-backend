package com.williameliasson.journal_backend.dtos;

import java.util.List;

public record StatisticsResponse(String username, List<MoodStat> moodStats, Integer journalEntryCount) {
}
