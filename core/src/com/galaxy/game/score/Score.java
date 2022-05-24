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

    private static int points;
    private static String time;

    public Score(){
        points = 0;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
    }

    public Score(int points){ //dev
        this.points = points;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
    }

    public static void addPoints(Enemy enemy){
        points += enemy.getEnemyValue();
    }

    public static int getPoints(){
        return points;
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
        if (this.getPoints() < o.getPoints()) return 1;
        else if (this.getPoints() == o.getPoints()) return 0;
        else return -1;
    }
}
