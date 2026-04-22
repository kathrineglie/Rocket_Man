package inf112.rocketman.model.obstacles;

import inf112.rocketman.model.obstacles.flames.Flame;
import inf112.rocketman.model.obstacles.lazers.Lazer;
import inf112.rocketman.model.obstacles.rockets.Rocket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ObstacleManagerTest {

    private IRandomObstacleFactory factory;
    private ObstacleManager manager;

    /**
     * Sets up a mocked obstacle factory and a fresh ObstacleManager before each test.
     *
     * The mock is configured to return a valid test rocket whenever a new obstacle
     * is requested. This prevents null values during update tests, since the manager
     * attempts to spawn a new obstacle when its timer reaches zero.
     *
     * This allows the tests to run in a controlled way without depending on the
     * real random obstacle factory.
     */
    @BeforeEach
    void setUp() {
        factory = mock(IRandomObstacleFactory.class);
        manager = new ObstacleManager(factory);

        when(factory.newObstacle(
                any(),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat()
        )).thenReturn(new TestRocket(500, 200, 50, 30, -100, 0));
    }

    @Test
    void testSetAndGetObstacleSpawnInterval() {
        manager.setObstacleSpawnInterval(1.5f);

        assertEquals(1.5f, manager.getObstacleSpawnInterval());
    }

    @Test
    void testUpdateSpawnsObstacleWhenTimerExpires() {
        TestRocket rocket = new TestRocket(900, 200, 50, 30, -100, 0);

        when(factory.newObstacle(
                any(),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat()
        )).thenReturn(rocket);

        manager.update(3.0f, 1000f, 800f, 120f, 5f, 2, -350f, -550f);

        assertEquals(1, manager.getObstacles().size());
    }

    @Test
    void testUpdateRemovesFinishedLazer() {
        TestLazer lazer = new TestLazer(200, 200, 300, 20, 0, 0);
        lazer.setProgressionLevel(4);

        manager.getObstacleListReference().add(lazer);

        manager.update(0.1f, 1000f, 800f, 120f, 5f, 1, -350f, -550f);

        assertEquals(1, manager.getObstacles().size());
        assertFalse(manager.getObstacles().contains(lazer));
    }

    @Test
    void testUpdateRemovesRocketWhenOffScreen() {
        TestRocket rocket = new TestRocket(-100, 200, 50, 30, -100, 0);

        manager.getObstacleListReference().add(rocket);

        manager.update(0.1f, 1000f, 800f, 120f, 5f, 1, -350f, -550f);

        assertEquals(1, manager.getObstacles().size());
        assertFalse(manager.getObstacles().contains(rocket));
    }



    @Test
    void testClearRemovesAllObstacles() {
        manager.getObstacleListReference().add(new TestRocket(500, 200, 50, 30, -100, 0));
        manager.getObstacleListReference().add(new TestLazer(200, 200, 300, 20, 0, 0));

        manager.clear();

        assertTrue(manager.getObstacles().isEmpty());
    }

    @Test
    void testResetClearsObstaclesAndResetsSpawnInterval() {
        manager.setObstacleSpawnInterval(1.2f);
        manager.getObstacleListReference().add(new TestRocket(500, 200, 50, 30, -100, 0));

        manager.reset();

        assertTrue(manager.getObstacles().isEmpty());
        assertEquals(2.5f, manager.getObstacleSpawnInterval());
    }

    @Test
    void testGetObstacleListReferenceReturnsCopy() {
        manager.addObstacleForTesting(new TestRocket(500, 200, 50, 30, -100, 0));

        var list1 = manager.getObstacleListReference();
        var list2 = manager.getObstacleListReference();

        assertNotSame(list1, list2);
        assertEquals(1, list1.size());
        assertEquals(1, list2.size());
    }

    @Test
    void testGetNonOverlappingLazerReturnsNullWhenAllCandidatesOverlap() throws Exception {
        TestLazer existing = new TestLazer(100, 100, 200, 20, 0, 0);
        existing.setProgressionLevel(3);
        manager.addObstacleForTesting(existing);

        TestLazer overlappingCandidate = new TestLazer(100, 100, 200, 20, 0, 0);

        when(factory.newObstacle(
                eq(ObstacleType.LAZER),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat()
        )).thenReturn(overlappingCandidate);

        var method = ObstacleManager.class.getDeclaredMethod(
                "getNonOverlappingLazer",
                float.class, float.class, float.class, float.class
        );
        method.setAccessible(true);

        Object result = method.invoke(manager, 1000f, 800f, 120f, 5f);

        assertNull(result);
    }


    @Test
    void testGetNonOverlappingLazerReturnsLazerWhenNoOverlap() throws Exception {
        TestLazer candidate = new TestLazer(400, 100, 200, 20, 0, 0);

        when(factory.newObstacle(
                eq(ObstacleType.LAZER),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat()
        )).thenReturn(candidate);

        var method = ObstacleManager.class.getDeclaredMethod(
                "getNonOverlappingLazer",
                float.class, float.class, float.class, float.class
        );
        method.setAccessible(true);

        Object result = method.invoke(manager, 1000f, 800f, 120f, 5f);

        assertNotNull(result);
        assertSame(candidate, result);
    }

    @Test
    void testFinishedLazerDoesNotBlockNewLazerSpawn() throws Exception {
        TestLazer finishedLazer = new TestLazer(100, 100, 200, 20, 0, 0);
        finishedLazer.setProgressionLevel(4);
        manager.getObstacleListReference().add(finishedLazer);

        TestLazer candidate = new TestLazer(100, 100, 200, 20, 0, 0);

        when(factory.newObstacle(
                eq(ObstacleType.LAZER),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat(),
                anyFloat()
        )).thenReturn(candidate);

        var method = ObstacleManager.class.getDeclaredMethod(
                "getNonOverlappingLazer",
                float.class, float.class, float.class, float.class
        );
        method.setAccessible(true);

        Object result = method.invoke(manager, 1000f, 800f, 120f, 5f);

        assertSame(candidate, result);
    }

    private static class TestRocket extends Rocket {
        protected TestRocket(float x, float y, float width, float height, float vx, float vy) {
            super(x, y, width, height, vx, vy);
        }
    }

    private static class TestLazer extends Lazer {
        protected TestLazer(float x, float y, float width, float height, float vx, float vy) {
            super(x, y, width, height, vx, vy);
        }
    }

    private static class TestFlame extends Flame {
        protected TestFlame(float x, float y, float width, float length, float vx, float vy, float angle) {
            super(x, y, width, length, vx, vy, angle);
        }
    }
}