package inf112.rocketman.model.progress;

import com.badlogic.gdx.Preferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlayerProgressManagerTest {

    private Preferences highscores;
    private Preferences coins;
    private PlayerProgressManager progressManager;

    /**
     * Sets up mocked Preferences objects and a PlayerProgressManager before each test.
     *
     * The mocks are configured to behave like empty saved storage:
     * - highscores.get() returns an empty map
     * - coins.get() returns an empty map
     * - getInteger(key, defaultValue) returns the provided default value
     * - getInteger(key) returns 0
     *
     * This allows the tests to run without requiring a real libGDX Application
     * or actual saved preferences.
     */
    @BeforeEach
    public void setUp() {
        highscores = mock(Preferences.class);
        when(highscores.get()).thenReturn(new HashMap<>());
        when(highscores.getInteger(anyString(), anyInt())).thenAnswer(invocation -> invocation.getArgument(1));
        when(highscores.getInteger(anyString())).thenReturn(0);

        coins = mock(Preferences.class);
        when(coins.get()).thenReturn(new HashMap<>());
        when(coins.getInteger(anyString(), anyInt())).thenAnswer(invocation -> invocation.getArgument(1));
        when(coins.getInteger(anyString())).thenReturn(0);

        progressManager = new PlayerProgressManager(highscores, coins);
    }

    @Test
    void testHighscoresAreSorted() {
        Map<String, Integer> fakeData = new HashMap<>();
        fakeData.put("thirdPlace", 50);
        fakeData.put("firstPlace", 200);
        fakeData.put("secondPlace", 100);

        doReturn(fakeData).when(highscores).get();
        when(highscores.getInteger("thirdPlace")).thenReturn(50);
        when(highscores.getInteger("firstPlace")).thenReturn(200);
        when(highscores.getInteger("secondPlace")).thenReturn(100);

        List<Map.Entry<String, Integer>> sorted = progressManager.getSortedHighScoreList();

        assertEquals("firstPlace", sorted.getFirst().getKey());
        assertEquals(200, sorted.getFirst().getValue());
        assertEquals("secondPlace", sorted.get(1).getKey());
        assertEquals(100, sorted.get(1).getValue());
        assertEquals("thirdPlace", sorted.get(2).getKey());
        assertEquals(50, sorted.get(2).getValue());
    }

    @Test
    void testSameNameOnHighscoreBoard() {
        String playerName = "TestPlayer";

        Map<String, Integer> currentScores = new HashMap<>();
        currentScores.put(playerName, 100);

        doReturn(currentScores).when(highscores).get();
        when(highscores.getInteger(playerName, 0)).thenReturn(100);

        progressManager.updateHighscores(playerName, 50);

        verify(highscores, never()).putInteger(eq(playerName), anyInt());

        clearInvocations(highscores);

        doReturn(currentScores).when(highscores).get();
        when(highscores.getInteger(playerName, 0)).thenReturn(100);

        progressManager.updateHighscores(playerName, 150);

        verify(highscores).putInteger(playerName, 150);
        verify(highscores).flush();
    }

    @Test
    void testAddCoinsAddsToExistingAmount() {
        when(coins.getInteger("Bob", 0)).thenReturn(7);

        progressManager.addCoins("Bob", 3);

        verify(coins).putInteger("Bob", 10);
        verify(coins).flush();
    }

    @Test
    void testAddCoinsStartsFromZeroWhenPlayerHasNoCoins() {
        when(coins.getInteger("Bob", 0)).thenReturn(0);

        progressManager.addCoins("Bob", 5);

        verify(coins).putInteger("Bob", 5);
        verify(coins).flush();
    }

    @Test
    void testUpdateHighscoresReplacesLowestScoreWhenListIsFullAndNewScoreIsHigher() {
        Map<String, Integer> fakeData = new HashMap<>();
        fakeData.put("A", 100);
        fakeData.put("B", 200);
        fakeData.put("C", 300);
        fakeData.put("D", 400);
        fakeData.put("E", 500);

        doReturn(fakeData).when(highscores).get();
        when(highscores.getInteger("A")).thenReturn(100);
        when(highscores.getInteger("B")).thenReturn(200);
        when(highscores.getInteger("C")).thenReturn(300);
        when(highscores.getInteger("D")).thenReturn(400);
        when(highscores.getInteger("E")).thenReturn(500);
        when(highscores.getInteger("NewPlayer", 0)).thenReturn(0);

        progressManager.updateHighscores("NewPlayer", 250);

        verify(highscores).remove("A");
        verify(highscores).putInteger("NewPlayer", 250);
        verify(highscores).flush();
    }

    @Test
    void testUpdateHighscoresDoesNotReplaceWhenListIsFullAndNewScoreIsTooLow() {
        Map<String, Integer> fakeData = new HashMap<>();
        fakeData.put("A", 100);
        fakeData.put("B", 200);
        fakeData.put("C", 300);
        fakeData.put("D", 400);
        fakeData.put("E", 500);

        doReturn(fakeData).when(highscores).get();
        when(highscores.getInteger("A")).thenReturn(100);
        when(highscores.getInteger("B")).thenReturn(200);
        when(highscores.getInteger("C")).thenReturn(300);
        when(highscores.getInteger("D")).thenReturn(400);
        when(highscores.getInteger("E")).thenReturn(500);
        when(highscores.getInteger("NewPlayer", 0)).thenReturn(0);

        progressManager.updateHighscores("NewPlayer", 50);

        verify(highscores, never()).remove(anyString());
        verify(highscores, never()).putInteger(eq("NewPlayer"), anyInt());
    }



}