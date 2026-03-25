package inf112.rocketman.model.Difficulty;

import java.util.ArrayList;
import java.util.List;

public class DifficultyManager {

    private final float initialLevelInterval;
    private final float levelGrowth;
    private final float baseScrollSpeed;
    private final float speedIncreasePerLevel;
    private final float baseFlameSpawnInterval;
    private final float baseLazerSpawnInterval;
    private final float baseRocketSpawnInterval;
    private final int absoluteMaxObstacles;

    private final float maxScrollSpeed;


    public DifficultyManager(float initialLevelInterval, float levelGrowth, float baseScrollSpeed, float speedIncreasePerLevel, float maxScrollSpeed, float baseFlameSpawnInterval,float baseLazerSpawnInterval, float baseRocketSpawnInterval, int absoluteMaxObstacles){
        this.initialLevelInterval = initialLevelInterval;
        this.levelGrowth = levelGrowth;
        this.baseScrollSpeed = baseScrollSpeed;
        this.speedIncreasePerLevel = speedIncreasePerLevel;
        this.maxScrollSpeed = maxScrollSpeed;
        this.baseFlameSpawnInterval = baseFlameSpawnInterval;
        this.baseLazerSpawnInterval = baseLazerSpawnInterval;
        this.baseRocketSpawnInterval = baseRocketSpawnInterval;
        this.absoluteMaxObstacles = absoluteMaxObstacles;

    }

    public DifficultySettings getSettings(float distanceMeters){
        int level = getDifficultyLevel(distanceMeters);

        float scrollSpeed = baseScrollSpeed * (float) Math.pow(1 + speedIncreasePerLevel, level -1);
        scrollSpeed = Math.max(scrollSpeed, maxScrollSpeed);


        float flameSpawnInterval = Math.max(0.8f, baseFlameSpawnInterval * (float) Math.pow(0.95, level -1));
        float lazerSpawnInterval = Math.max(1.2f, baseLazerSpawnInterval * (float) Math.pow(0.95, level -1));
        float rocketSpawnInterval = Math.max(2.0f, baseRocketSpawnInterval * (float) Math.pow(0.95, level -1));

        int maxObstacles = getMaxObstacles(level);


        return new DifficultySettings(level, scrollSpeed, flameSpawnInterval,lazerSpawnInterval, rocketSpawnInterval, maxObstacles, List.copyOf(getAllowedPatterns(level)));
    }

    private int getDifficultyLevel(float distanceMeters){
        float nextThreshold = initialLevelInterval;
        float interval = initialLevelInterval;
        int level = 1;

        while (distanceMeters >= nextThreshold){
            level++;
            interval *= levelGrowth;
            nextThreshold += interval;
        }

        return level;
    }

    private int getMaxObstacles(int level){
        int maxObstacles = 2;

        if (level >= 2){
            maxObstacles = 3;
        }

        if (level >=3){
            maxObstacles = 4;
        }

        if (level >= 4){
            maxObstacles = 4 + (level -3);
        }

        return Math.min(maxObstacles, absoluteMaxObstacles);
    }


    private List<PatternType> getAllowedPatterns(int level){
        List<PatternType> patterns = new ArrayList<>();

        patterns.add(PatternType.FLAME);

        if (level >=2){
            patterns.add(PatternType.LAZER);
        }

        if (level >= 3){
            patterns.add(PatternType.ROCKET);
        }

        return patterns;
    }


}
