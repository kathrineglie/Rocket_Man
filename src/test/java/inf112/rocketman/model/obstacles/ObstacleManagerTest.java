package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import inf112.rocketman.model.WorldDimensions;
import inf112.rocketman.model.obstacles.flames.Flame;
import inf112.rocketman.model.obstacles.lazers.Lazer;
import inf112.rocketman.model.obstacles.rockets.Rocket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObstacleManagerTest {

    private IRandomObstacleFactory factory;
    private ObstacleManager manager;
    private WorldDimensions dimensions;

    private static final float WORLD_WIDTH = 1000f;
    private static final float WORLD_HEIGHT = 800f;
    private static final float GROUND = 120f;
    private static final float MARGIN = 5f;
    private static final float VX = -350f;

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
        dimensions = new WorldDimensions(WORLD_WIDTH, WORLD_HEIGHT);

        when(factory.newObstacle(
                any(ObstacleType.class),
                any(WorldDimensions.class),
                anyFloat(),
                anyFloat(),
                anyFloat()
        )).thenReturn(new TestRocket(500, 200, 50, 30, -100, 0, GROUND));
    }

    @Test
    void testSetAndGetObstacleSpawnInterval() {
        manager.setObstacleSpawnInterval(1.5f);
        assertEquals(1.5f, manager.getObstacleSpawnInterval());
    }

    @Test
    void testUpdateSpawnsObstacleWhenTimerExpires() {
        TestRocket rocket = new TestRocket(900, 200, 50, 30, -100, 0, 50);

        when(factory.newObstacle(
                any(ObstacleType.class),
                any(WorldDimensions.class),
                anyFloat(),
                anyFloat(),
                anyFloat()
        )).thenReturn(rocket);

        manager.update(3.0f, dimensions, GROUND, MARGIN, 2, VX, -550f);

        assertTrue(manager.getObstacles().contains(rocket));
    }

    @Test
    void testUpdateRemovesFinishedLazer() {
        TestLazer lazer = new TestLazer(200, 200, 300, 20, 0, 0, GROUND);
        lazer.setProgressionLevel(4);

        manager.getObstacleListReference().add(lazer);

        manager.update(0.1f, dimensions, GROUND, MARGIN, 1, VX, -550f);

        assertFalse(manager.getObstacles().contains(lazer));
    }

    @Test
    void testUpdateRemovesRocketWhenOffScreen() {
        TestRocket rocket = new TestRocket(-100, 200, 50, 30, -100, 0, 50);

        manager.getObstacleListReference().add(rocket);

        manager.update(0.1f, dimensions, GROUND, MARGIN, 1, -350f, -550f);

        assertFalse(manager.getObstacles().contains(rocket));
    }



    @Test
    void testClearRemovesAllObstacles() {
        manager.getObstacleListReference().add(new TestRocket(500, 200, 50, 30, -100, 0, 50));
        manager.getObstacleListReference().add(new TestLazer(200, 200, 300, 20, 0, 0, 50));

        manager.clear();

        assertTrue(manager.getObstacles().isEmpty());
    }

    @Test
    void testResetClearsObstaclesAndResetsSpawnInterval() {
        manager.setObstacleSpawnInterval(1.2f);
        manager.getObstacleListReference().add(new TestRocket(500, 200, 50, 30, -100, 0, 50));

        manager.reset();

        assertTrue(manager.getObstacles().isEmpty());
        assertEquals(2.5f, manager.getObstacleSpawnInterval());
    }

    @Test
    void testGetObstacleListReferenceReturnsCopy() {
        manager.addObstacleForTesting(new TestRocket(500, 200, 50, 30, -100, 0, 50));

        var list1 = manager.getObstacleListReference();
        var list2 = manager.getObstacleListReference();

        assertNotSame(list1, list2);
        assertEquals(1, list1.size());
        assertEquals(1, list2.size());
    }

//    @Test
//    void testGetNonOverlappingLazerReturnsNullWhenAllCandidatesOverlap() throws Exception {
//        TestLazer existing = new TestLazer(100, 100, 200, 20, 0, 0, 50);
//        existing.setProgressionLevel(3);
//        manager.addObstacleForTesting(existing);
//
//        TestLazer overlappingCandidate = new TestLazer(100, 100, 200, 20, 0, 0, 50);
//
//        when(factory.newObstacle(
//                eq(ObstacleType.LAZER),
//                any(WorldDimensions.class),
//                anyFloat(),
//                anyFloat(),
//                anyFloat()
//        )).thenReturn(overlappingCandidate);
//
//        var method = ObstacleManager.class.getDeclaredMethod(
//                "getNonOverlappingLazer",
//                float.class, float.class, float.class, float.class
//        );
//        method.setAccessible(true);
//
//        Object result = method.invoke(manager, 1000f, 800f, 120f, 5f);
//
//        assertNull(result);
//    }


//    @Test
//    void testGetNonOverlappingLazerReturnsLazerWhenNoOverlap() throws Exception {
//        TestLazer candidate = new TestLazer(400, 100, 200, 20, 0, 0, 50);
//
//        when(factory.newObstacle(
//                eq(ObstacleType.LAZER),
//                new WorldDimensions(anyFloat(), anyFloat()),
//                anyFloat(),
//                anyFloat(),
//                anyFloat()
//        )).thenReturn(candidate);
//
//        var method = ObstacleManager.class.getDeclaredMethod(
//                "getNonOverlappingLazer",
//                float.class, float.class, float.class, float.class
//        );
//        method.setAccessible(true);
//
//        Object result = method.invoke(manager, 1000f, 800f, 120f, 5f);
//
//        assertSame(candidate, result);
//    }

//    @Test
//    void testFinishedLazerDoesNotBlockNewLazerSpawn() throws Exception {
//        TestLazer finishedLazer = new TestLazer(100, 100, 200, 20, 0, 0, 50);
//        finishedLazer.setProgressionLevel(4);
//        manager.getObstacleListReference().add(finishedLazer);
//
//        TestLazer candidate = new TestLazer(100, 100, 200, 20, 0, 0, 50);
//
//        when(factory.newObstacle(
//                eq(ObstacleType.LAZER),
//                any(WorldDimensions.class),
//                anyFloat(),
//                anyFloat(),
//                anyFloat()
//        )).thenReturn(candidate);
//
//        var method = ObstacleManager.class.getDeclaredMethod(
//                "getNonOverlappingLazer",
//                float.class, float.class, float.class, float.class
//        );
//        method.setAccessible(true);
//
//        Object result = method.invoke(manager, 1000f, 800f, 120f, 5f);
//
//        assertSame(candidate, result);
//    }

    private static class TestRocket extends Rocket {
        protected TestRocket(float x, float y, float width, float height, float vx, float vy, float ground) {
            super(new Rectangle(x, y, width, height), new Velocity(vx, vy), ground);
        }
    }

    private static class TestLazer extends Lazer {
        protected TestLazer(float x, float y, float width, float height, float vx, float vy, float ground) {
            super(new Rectangle(x, y, width, height), new Velocity(vx, vy), ground);
        }
    }

    private static class TestFlame extends Flame {
        protected TestFlame(float x, float y, float width, float length, float vx, float vy, float ground, float angle) {
            super(new Rectangle(x, y, width, length), new Velocity(vx, vy), ground, angle);
        }
    }
}