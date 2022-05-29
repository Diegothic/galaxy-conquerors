package com.galaxy.game.score;

import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.enemy.Enemy;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Comparator;

//todo add enemy value in enemy class and add addPoints method in PlayerProjectile
public class Score implements Comparable<Score>{

    private int points;
    private static int pointsStatic;
    private String time;

    public Score(){
        points = 0;
        pointsStatic = 0;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(0);
    }

    public Score(int points){ //dev
        this.points = points;
        pointsStatic = points;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
    }

    public void addPoints(Enemy enemy){
        points += enemy.getEnemyValue();
        pointsStatic += enemy.getEnemyValue();
    }

//    public static void addPointsStatic(Enemy enemy){
//        pointsStatic += enemy.getEnemyValue();
//    }

    public int getPointsNonStatic(){return points;} //shenanigans

    public static int getPoints(){
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
        if (this.getPointsNonStatic() < o.getPointsNonStatic()) return 1;
        else if (this.getPointsNonStatic() == o.getPointsNonStatic()) return 0;
        else return -1;
    }
}
