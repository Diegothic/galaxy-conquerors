package com.galaxy.game.score;

import com.galaxy.game.entity.enemy.Enemy;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//todo add enemy value in enemy class and add addPoints method in PlayerProjectile
public class Score implements Comparable<Score> {

    private int points;
    private static int pointsStatic;
    private String time;

    public Score() {
        points = 0;
        pointsStatic = 0;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(0);
    }

    public Score(int points) { //dev
        this.points = points;
        pointsStatic = points;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
    }

    public static void resetPoints() {
        pointsStatic = 0;
    }

    public void addPoints(Enemy enemy) {
        points += enemy.getEnemyValue();
        pointsStatic += enemy.getEnemyValue();
    }

    public void addPoints(int amount) {
        points += amount;
        pointsStatic += amount;
    }

    public int getPointsNonStatic() {
        return points;
    } //shenanigans

    public static int getPoints() {
        return pointsStatic;
    }

    public String getDate() {
        return time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Score{" +
                "points=" + points +
                ", time='" + time + '\'' +
                '}';
    }


    @Override
    public int compareTo(Score o) {
        return Integer.compare(o.getPointsNonStatic(), this.getPointsNonStatic());
    }
}
