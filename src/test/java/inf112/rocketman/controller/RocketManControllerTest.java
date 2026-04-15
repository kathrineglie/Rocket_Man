package inf112.rocketman.controller;

import inf112.rocketman.view.RocketManView;
import inf112.rocketman.view.ViewableRocketManModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RocketManControllerTest {

    private ControllableRocketManModel model;
    private ViewableRocketManModel viewModel;
    private RocketManView view;
    private RocketManController controller;

    @BeforeEach
    void setUp(){
        model = mock(ControllableRocketManModel.class);
        viewModel = mock(ViewableRocketManModel.class);
        view = mock(RocketManView.class);

        controller = new RocketManController(model, viewModel, view);
    }

    @Test
    public void startGame_setsName_startsGame(){
        controller.startGame("Bob");

        verify(model).setPlayerName("Bob");
        verify(model).startNewGame();
        verify(view).playExclusiveMusic(anyString());
    }






}
