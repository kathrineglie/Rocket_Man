package inf112.rocketman.model.obstacles;

import inf112.rocketman.model.obstacles.lazers.Lazer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class ObstacleManagerTest {

    @Test
    void testGetNonOverlappingLazerReturnsLazerWhenNoOverlap() throws Exception {
        ObstacleManager obstacleManager = new ObstacleManager(new RandomObstacleFactory());

        Method method = ObstacleManager.class.getDeclaredMethod(
                "getNonOverlappingLazer",
                float.class, float.class, float.class, float.class
        );
        method.setAccessible(true);

        Object result = method.invoke(obstacleManager, 1000f, 800f, 120f, 5f);

        assertNotNull(result);
        assertTrue(result instanceof Lazer);
    }
}