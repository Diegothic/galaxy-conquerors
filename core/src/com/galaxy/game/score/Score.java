package com.galaxy.game.score;

import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.enemy.Enemy;

//todo add enemy value in enemy class and add addPoints method in PlayerProjectile
public class Score {

    private static int points;

    public Score(){
        points = 0;
    }

    public Score(int points){
        this.points = points;
    }

    public static void addPoints(Enemy enemy){
        points += 100; //dev
//        points += enemy.getRewardRatio() * 100;
    }

    public static int getPoints(){
        return points;
    }
}
