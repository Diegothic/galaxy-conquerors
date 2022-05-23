package com.galaxy.game.level;

public class GameMode {

    private final int maxLives;
    private int lives;

    private boolean finished;
    private boolean isLost;

    public GameMode(int maxLives) {
        this.maxLives = maxLives;
        lives = maxLives;

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
}
