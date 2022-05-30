package com.galaxy.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    private final Font title;
    private final Font score;
    private final Font anyButtonText;
    private final OrthographicCamera camera;

    private List<Score> scoreList;

    private final Texture backgroundTexture;
    private final Sprite background;
    private float elapsed;

    public ScoreboardScreen(GalaxyConquerors game, Scoreboard scoreboard) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        title = new Font(25);
        score = new Font(12);
        scoreList = new ArrayList<>();
        scoreList = scoreboard.getScoreList();
        anyButtonText = new Font(20);

        backgroundTexture = new Texture(Gdx.files.internal("other/background_galaxy_2.png"));
        background = new Sprite(backgroundTexture);
        background.setPosition(
                VIEWPORT_WIDTH / 2.0f - background.getWidth() / 2.0f,
                VIEWPORT_HEIGHT / 2.0f - background.getHeight() / 2.0f
        );
        elapsed = 0.0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsed += delta;
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        background.setRotation(elapsed);
        background.draw(game.batch);
        title.printText(game.batch, "SCOREBOARD", new Vector2(105, 225));
        int i = 1;
        for (var score : scoreList) {
            var textPos = new Vector2(25, 210 - (i * 15));
            if (score.getPointsNonStatic() == 0) {
                this.score.printText(game.batch,
                        i + ": " + "--------------------------------------",
                        textPos);
            } else {
                this.score.printText(game.batch,
                        i + ": " + score.getPointsNonStatic() + "   " + score.getTime(),
                        textPos);
            }
            i++;
        }
        anyButtonText.printText(game.batch, "PRESS ANY KEY TO TRY AGAIN", new Vector2(25, 35));
        game.batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen(game));
        }
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
        backgroundTexture.dispose();
    }
}
