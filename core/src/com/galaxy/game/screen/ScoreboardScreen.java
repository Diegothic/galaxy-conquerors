package com.galaxy.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.galaxy.game.GalaxyConquerors;
import com.galaxy.game.score.Score;
import com.galaxy.game.score.Scoreboard;
import com.galaxy.game.util.Font;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardScreen implements Screen {

    private final GalaxyConquerors game;
    private static final int VIEWPORT_WIDTH = 426;
    private static final int VIEWPORT_HEIGHT = 240;

    private Font title, score, anyButtonText;
    private SpriteBatch batch;
    private final OrthographicCamera camera;

    private Scoreboard scoreboard;
    private List<Score> scoreList;

    public ScoreboardScreen(GalaxyConquerors game, Scoreboard scoreboard) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        title = new Font(25);
        score = new Font(12);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        this.scoreboard = scoreboard;
        scoreList = new ArrayList<>();
        scoreboard.addScore(Score.getPoints());
        scoreList = scoreboard.getScoreList();
        anyButtonText = new Font(30);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        title.printText(batch, "Scoreboard", new Vector2(95, 225));
        int i=1;
        for (Score x: scoreList){
            score.printText(batch,
                            i + ": " +
                                x.getPointsNonStatic()
                                + "   " + x.getTime(),
                                new Vector2(25, 220 - (i*15)));
            i++;
        }
        anyButtonText.printText(batch, "PRESS ANY KEY", new Vector2(45, 35));
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        float newHeight = VIEWPORT_WIDTH * ((float) height / (float) width);
        camera.viewportWidth = VIEWPORT_WIDTH;
        camera.viewportHeight = newHeight;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
