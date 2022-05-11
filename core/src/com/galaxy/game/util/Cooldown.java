package com.galaxy.game.util;

public class Cooldown {

    public float resetTime;
    private float currentTime;

    public Cooldown(float resetTime) {
        this.resetTime = resetTime;
        currentTime = resetTime;
    }

    public boolean isReady() {
        return currentTime >= resetTime;
    }

    public void step(float time) {
        if (currentTime < resetTime) {
            currentTime += time;
        }
    }

    public void reset() {
        currentTime = 0.0f;
    }
}
