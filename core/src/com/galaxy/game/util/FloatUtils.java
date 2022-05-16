package com.galaxy.game.util;

public class FloatUtils {

    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }
}
