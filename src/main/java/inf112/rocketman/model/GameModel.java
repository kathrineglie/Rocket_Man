package inf112.rocketman.model;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.controller.ControllableRocketManModel;
import inf112.rocketman.model.Obstacles.Flames.Flame;
import inf112.rocketman.model.Obstacles.Flames.FlameFactory;
import inf112.rocketman.model.Obstacles.Flames.RandomFlameFactory;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Obstacles.Lazers.Lazer;
import inf112.rocketman.model.Obstacles.Lazers.LazerFactory;
import inf112.rocketman.model.Obstacles.Lazers.RandomLazerFactory;
import inf112.rocketman.model.Obstacles.Obstacle;
import inf112.rocketman.model.Obstacles.Rockets.RandomRocketFactory;
import inf112.rocketman.model.Obstacles.Rockets.Rocket;
import inf112.rocketman.model.Obstacles.Rockets.RocketFactory;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.PowerUps.PowerUp;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.model.PowerUps.Bird;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private final TPowah player;

    private final float THRUST = 4000f;
    private final float MAX_VY = 700f;
    private final float GRAVITY = -1000f;
    private final float PLAYER_X = 300f;
    private final float PLAYER_Y = 100f;
    private boolean thrusting;

    private final float worldHeight;
    private final float worldWidth;
    private final float margin = 5;
    private final float BG_SPEED = 120f;

    private GameState gameState = GameState.HOME_SCREEN;

    private float bgScrollX = 0f;

    List<IObstacle> obstacles = new ArrayList<>();
    private RocketFactory rocketFactory = new RandomRocketFactory();
    private LazerFactory lazerFactory = new RandomLazerFactory();
    private FlameFactory flameFactory = new RandomFlameFactory();

    private float obstacleTimer = 0f;
    private float obstacleSpawnInteval = 2.5f;

    private Bird bird;
    private boolean birdActive = false;
    private PowerUp powerUp;
    private float powerUpTimer = 0f;
    private float powerUpSpawnInterval = 8f;


    public GameModel(float worldWidth, float worldHeight) {
        float pWidth = worldWidth/13;
        float pHeight= worldHeight/7;
        player = new TPowah(PLAYER_X,PLAYER_Y , pWidth, pHeight);
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    /**
     * Updates all of the characters, the background, the gamestate. Everything that has to do with the gamelogic
     *
     * @param dt The delta dime (seconds) since last update.
     * @param thrustingInput True if the player is currently applying thrust to the rocket.
     */
    public  void update (float dt, boolean thrustingInput) {
        if (gameState != GameState.PLAYING){
            return;
        }

        thrusting = thrustingInput;
        if (birdActive && bird != null) {
            bird.update(dt, thrustingInput, worldHeight);
        } else {
            player.update(dt, thrustingInput, worldHeight, THRUST, GRAVITY, MAX_VY);
        }

        updateBackground(dt);
        updateObstacle(dt);
        //updatePowerUp(dt);
        //checkPowerUpCollision();
        handleObstacleCollision();
    }

    private void checkPowerUpCollision() {
        if (powerUp == null) {
            return;
        }

        Rectangle playerHitbox = getPlayerHitbox();
        if (playerHitbox.overlaps(powerUp.getHitBox())) {
            activateBirdPowerUp();
            powerUp = null;
        }
    }

    /**
     * Handles collision of the differnt objects and powerups
     */

    private void handleObstacleCollision() {
        Rectangle playerHitbox = player.getHitBox();

        Iterator<IObstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            IObstacle obstacle = iterator.next();
            if (isNonActiveLazer(obstacle)) {
                continue;
            }

            if (!(obstacle instanceof Flame)) {
                Rectangle obstacleHitbox = obstacle.getHitBox();

                if (obstacleHitbox == null) {
                    continue;
                }

                if (player.getHitBox().overlaps(obstacleHitbox)) {
                    if (birdActive) {
                        //deactivateBirdPowerUp();
                        iterator.remove();
                    } else {

                        gameState = GameState.GAME_OVER;
                    }
                    return;
                }
            } else {
                handleFlameCollision(obstacle);
            }
        }
    }

    /**
     * Handles collision for flame object
     * @param obstacle
     */
    public void handleFlameCollision(IObstacle obstacle) {
        if (!(obstacle instanceof Flame)) {
            return;
        }

        Polygon polyHitBox = player.getPolyHitBox();

        if (Intersector.overlapConvexPolygons(((Flame) obstacle).getPolygon(), polyHitBox)) {
            if (birdActive) {
                //deactivateBirdPowerUp();
            } else {
                gameState = GameState.GAME_OVER;
            }
        }
    }

    public boolean isNonActiveLazer(IObstacle obstacle) {
        return obstacle instanceof Lazer && ((Lazer) obstacle).getProgressionLevel() != 3;
    }

    /**
     * Checks if the obstacles are off-screen and removes them if they are
     *
     * @param dt
     */
    private void updateObstacle(float dt) {
        obstacleTimer -= dt;

        if (obstacleTimer <= 0) {

            obstacles.add(getRandomObstacle());
            obstacleTimer = obstacleSpawnInteval;
        }

        Iterator<IObstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            IObstacle obstacle = iterator.next();
            obstacle.update(dt);

            if (obstacle instanceof Rocket && obstacle.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            }
            if (obstacle instanceof Lazer && ((Lazer) obstacle).getProgressionLevel() == 4) {
                iterator.remove();
            }
            if (obstacle instanceof Flame && obstacle.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            }
        }
    }

    /**
     * This method gets a random obstacle of the ones listed in the cases
     *
     * @return returns a random obstacle of the specified obstacles
     */
    private Obstacle getRandomObstacle() {
        Random random = new Random();
        int randNum = random.nextInt(1, 4);
        return switch (randNum) {
            case 1 -> rocketFactory.newRocket(worldWidth, worldHeight, margin);
            case 2 -> lazerFactory.newLazer(worldWidth, worldHeight, margin);
            case 3 -> flameFactory.newFlame(worldWidth, worldHeight, margin, BG_SPEED);
            default -> throw new RuntimeException("No object was chosen. The random number was: " + randNum);
        };
    }

    /**
     * Gets a list of the obstacles
     *
     * @return
     */
     public List<IObstacle> getObstacles() {
        return obstacles;
     }

    private void updatePowerUp(float dt){
        if (birdActive){
            return;
        }

        powerUpTimer -= dt;

        if (powerUp == null && powerUpTimer <= 0) {
            float width = 60f;
            float height = 60f;
            float x = worldWidth;
            float y = 250f + (float)Math.random() * (worldHeight - 400f);
            float vx = -250f;

            powerUp = new PowerUp(x, y, width, height, vx);
            powerUpTimer = powerUpSpawnInterval;
        }

        if (powerUp != null) {
            powerUp.update(dt);

            if (powerUp.isOfScreen(worldWidth, worldHeight)) {
                powerUp = null;
            }
        }
    }

    private void deactivateBirdPowerUp() {
        if (bird != null) {
            player.setX(bird.getX());
            player.setY(bird.getY());
            player.setVy(0);
        }

        bird = null;
        birdActive = false;
    }

    private void activateBirdPowerUp() {
        bird = new Bird(
                player.getX(),
                player.getY(),
                player.getWidth(),
                player.getHeight(),
                170f
        );
        birdActive = true;
    }

    // private Rectangle getActiveCharacterHitbox() {
    //     if (birdActive && bird != null) {
    //         return new Rectangle(bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());
    //     }
    //     return new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    // }

    private void updateBackground(float dt) {
        bgScrollX += BG_SPEED * dt;
        if (worldWidth > 0) {
            bgScrollX = bgScrollX % worldWidth;
        } else {
            bgScrollX = 0;
        }
    }

    @Override
    public float getWorldHeight(){
        return worldHeight;
    }

    @Override
    public float getWorldWidth(){
        return worldWidth;
    }

    @Override
    public float getBackgroundScrollX() {
        return bgScrollX;
    }

    public Rectangle getPlayerHitbox() {
        return player.getHitBox();
    }

    @Override
    public boolean isThrusting(){
        return thrusting;
    }

    @Override
    public PowerUp getPowerUp() {
        return powerUp;
    }

    @Override
    public boolean hasBirdPowerUp() {
        return birdActive;
    }

    @Override
    public Bird getBird() {
        return bird;
    }

    @Override
    public TPowah getPlayer() {
        return player;
    }

    @Override
    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void startGame(){
        obstacles.clear();

        player.setX(PLAYER_X);
        player.setY(PLAYER_Y);
        player.setVy(0);

        gameState = GameState.PLAYING;
    }

    @Override
    public void goToHomescreen(){
        obstacles.clear();
        gameState = gameState.HOME_SCREEN;
    }

    @Override
    public void pauseGame(){}
    @Override
    public void resumeGame(){}

    @Override
    public void endGame(){}

    @Override
    public void showInstructions() {
        gameState = gameState.INSTRUCTIONS;
    }

}
