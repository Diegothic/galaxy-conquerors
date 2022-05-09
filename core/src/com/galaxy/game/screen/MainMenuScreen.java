package com.galaxy.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;
import com.galaxy.game.GalaxyConquerors;
import com.galaxy.game.util.Font;

import java.util.logging.Handler;

public class MainMenuScreen implements Screen {

    private static final int VIEWPORT_WIDTH = 426;
    private static final int VIEWPORT_HEIGHT = 240;

    private final GalaxyConquerors game;
    private Font title, startText;
    private SpriteBatch batch;


    private final OrthographicCamera camera;

    public MainMenuScreen(GalaxyConquerors game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        title = new Font(25);
        startText = new Font(10);
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    if (delta % 10000 == 0) {
        title.font.draw(batch, "GALAXY CONQUERORS", 15, 220);
        startText.font.draw(batch, "PRESS SPACE TO START", 100, 60); //epilepsja hardo
    }
    batch.end();
    }

    public void resize(int width, int height) {

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
