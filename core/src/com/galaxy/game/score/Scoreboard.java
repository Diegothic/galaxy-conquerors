package com.galaxy.game.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Scoreboard extends ArrayList<Score>{

    private List<Score> scoreList = new ArrayList<>();
    private FileHandle dir;

    public Scoreboard(FileHandle dir){
        this.dir = dir;
        loadScore();
    }

    private void loadScore(){
        JsonReader jsonReader = new JsonReader();
        FileHandle jsonHandle;
        try {
            jsonHandle = dir;
            JsonValue jsonRaw = jsonReader.parse(jsonHandle);
            Type listType = new TypeToken<ArrayList<Score>>(){}.getType();
            scoreList = new Gson().fromJson(jsonRaw.toJson(JsonWriter.OutputType.json), listType);
        }
        catch (Exception e){
            for (int i=0;i<10;i++){
                scoreList.add(new Score());
                System.out.println(scoreList.get(i).toString());
            }
            writeScoreToJson();
        }
    }

    public List<Score> getScoreList(){
        return scoreList;
    }

    public void writeScoreToJson(){
        String json = new Gson().toJson(scoreList);
        try {
            File file = new File(dir.path());
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addScore(int score){
        scoreList.add(new Score(score));
        Collections.sort(scoreList, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o1.compareTo(o2);
            }
        });
        trimScoreboard(10);
        writeScoreToJson();
    }

    private void trimScoreboard(int size){
        for (int i=size-1;i< scoreList.size();i++){
            scoreList.remove(i);
        }
    }
}
