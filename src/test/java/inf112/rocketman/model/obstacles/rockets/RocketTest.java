package inf112.rocketman.model.obstacles.rockets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RocketTest {
    private Rocket rocket;

    @BeforeEach
    void setUp() {
        Rocket.setState(false);
        rocket = new Rocket(10f, 20f, 5f, 5f, -3f, 0);
    }

    @Test
    void updateDecreasesSpawnTimerWhenInactive() {
        float before = rocket.getSpawnTimer();

        rocket.update(0.5f);

        assertEquals(before - 0.5f, rocket.getSpawnTimer(), 0.0001f);
        assertFalse(rocket.isActive());
    }

    @Test
    void updateActiveRocketWhenSpawnTimerReachesZero() {
        rocket.update(1.5f);

        assertTrue(rocket.isActive());
    }

    @Test
    void updateDoesNotMoveRocketWhenInactive() {
        float startX = rocket.getX();
        float startY = rocket.getY();

        rocket.update(0.5f);

        assertEquals(startX, rocket.getX(), 0.0001f);
        assertEquals(startY, rocket.getY(), 0.0001f);
        assertFalse(rocket.isActive());
    }

    @Test
    void updateMovesRocketWhenActive() {
        Rocket.setState(true);

        float startX = rocket.getX();
        float startY = rocket.getY();

        rocket.update(1.0f);

        assertEquals(startX-3f, rocket.getX(), 0.0001f);
        assertEquals(startY, rocket.getY(), 0.0001f);
    }
}
