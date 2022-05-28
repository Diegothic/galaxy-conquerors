package com.galaxy.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.galaxy.game.GalaxyConquerors;
import com.galaxy.game.level.GameLevel;
import com.galaxy.game.level.Level_1;
import com.galaxy.game.score.Score;
import com.galaxy.game.ui.UI;
import com.galaxy.game.util.Font;

public class GameScreen implements Screen {

    private static final int VIEWPORT_WIDTH = 426;
    private static final int VIEWPORT_HEIGHT = 240;

    private final GalaxyConquerors game;
    private final OrthographicCamera camera;
    private final GameLevel level;
    private Font score;
    private UI ui;

    private boolean drawDebug;
    private boolean debugButtonPressed;

    public GameScreen(GalaxyConquerors game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        level = new Level_1(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        drawDebug = false;
        ui = new UI(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, level);
    }

    @Override
    public void show() {
        level.start();
    }

    @Override
    public void render(float delta) {
        boolean debugButtonWasPressed = debugButtonPressed;
        debugButtonPressed = Gdx.input.isKeyPressed(Input.Keys.F1);
        if (debugButtonPressed && !debugButtonWasPressed) {
            drawDebug = !drawDebug;
        }
        level.update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        level.render(game.batch);
        ui.render(game.batch);
        game.batch.end();
        if (drawDebug) {
            game.shapeRenderer.setProjectionMatrix(camera.combined);
            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            level.renderDebug(game.shapeRenderer);
            game.shapeRenderer.end();
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
        level.dispose();
    }
}
