package inf112.rocketman.model.PowerUps;

public interface PowerUpFactory {

    PowerUp newPowerUp(float worldWidth, float worldHeight, float ground, float margin, float bgSpeed);
}
