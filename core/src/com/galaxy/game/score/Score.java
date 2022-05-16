package com.galaxy.game.score;

import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.enemy.Enemy;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

//todo add enemy value in enemy class and add addPoints method in PlayerProjectile
public class Score {

    private int points;
    private String time;

    public Score(){
        points = 0;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public Score(int points){
        this.points = points;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public void addPoints(Enemy enemy){
        points += 100; //dev
//        points += enemy.getRewardRatio() * 100;
    }

    public int getPoints(){
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
}
