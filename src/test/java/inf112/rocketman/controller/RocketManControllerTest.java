package inf112.rocketman.controller;

import inf112.rocketman.model.GameState;
import inf112.rocketman.view.RocketManView;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.view.assets.RocketManAudio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

class RocketManControllerTest {

    private ControllableRocketManModel model;
    private RocketManView view;
    private ViewableRocketManModel viewModel;
    private RocketManController controller;
    private RocketManAudio audio;

    @BeforeEach
    void setUp(){
        model = mock(ControllableRocketManModel.class);
        viewModel = mock(ViewableRocketManModel.class);
        view = mock(RocketManView.class);
        audio = mock(RocketManAudio.class);

        controller = new RocketManController(model, viewModel, view, audio);
    }

    @Test
    void startGame_setsName_startsGame(){
        controller.startGame("Bob");

        verify(audio).stopAllMusic();
        verify(audio).playExclusiveMusic(anyString());
        verify(model).setPlayerName("Bob");
        verify(model).startNewGame();
    }

    @Test
    void create_playsMusic(){
        controller.create();

        verify(audio).playExclusiveMusic(anyString());
    }

    @Test
    void rezise_callsViewRezise(){
        controller.resize(1000, 800);

        verify(view).resize(1000, 800);
    }

    @Test
    void dispose_disposesViewAndAudio(){
        controller.dispose();

        verify(view).dispose();
        verify(audio).dispose();
    }

    @Test
    void getView_returnsView(){
        assertSame(view, controller.getView());
    }

    @Test
    void showInstruction_callsModelShowInstructions(){
        controller.showInstruction();

        verify(model).showInstructions();
    }

    @Test
    void getState_returnsModelState(){
        when(model.getGameState()).thenReturn(GameState.PLAYING);

        GameState state = controller.getState();

        assertEquals(GameState.PLAYING, state);
    }

    @Test
    void getViewableModel_returnsViewableModel() {
        assertSame(viewModel, controller.getViewableModel());
    }
}
