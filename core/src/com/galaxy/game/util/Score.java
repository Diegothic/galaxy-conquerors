package com.galaxy.game.util;

public class Score {

    private static int points;

    public Score(){
        points = 0;

    }

    public static void addScore(){
        points += 100;
    }

    public static int getScore(){
        return points;
    }

}
