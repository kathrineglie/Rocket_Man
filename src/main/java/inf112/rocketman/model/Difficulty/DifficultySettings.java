package inf112.rocketman.model.Difficulty;

import inf112.rocketman.model.Difficulty.PatternType;

import java.util.List;

public record DifficultySettings(
        int level,
        float scrollSpeed,
        float spawnInterval,
        List<PatternType> allowedPatterns
) { }
