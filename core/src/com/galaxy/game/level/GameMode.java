package com.galaxy.game.level;

public class GameMode {

    private final int maxLives;
    private int lives;

    private boolean finished;
    private boolean isLost;

    private int aliveEnemies;

    public GameMode(int maxLives) {
        this.maxLives = maxLives;
        lives = maxLives;

        finished = false;
        isLost = false;
    }

    public boolean shouldFinish() {
        return finished;
    }

    public boolean isLost() {
        return isLost;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        lives--;
        if (lives <= 0) {
            lives = 0;
            isLost = true;
            finished = true;
        }
    }

    public void setAliveEnemies(int aliveEnemies) {
        if (aliveEnemies < 0) {
            return;
        }
        this.aliveEnemies = aliveEnemies;
    }

    public void decreaseAliveEnemies() {
        aliveEnemies--;
        if (aliveEnemies <= 0) {
            aliveEnemies = 0;
            finished = true;
        }
    }
}
