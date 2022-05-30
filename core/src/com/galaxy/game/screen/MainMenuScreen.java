package com.galaxy.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.galaxy.game.GalaxyConquerors;
import com.galaxy.game.util.FloatUtils;
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
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    batch.begin();
    title.printText(batch, "GALAXY CONQUERORS", new Vector2(15,220));
    startText.printText(batch, "PRESS ANY KEY TO START", new Vector2(100, 60));
    batch.end();
    }

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
