package com.galaxy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.galaxy.game.screen.DevScreen;
import com.galaxy.game.screen.MainMenuScreen;

public class GalaxyConquerors extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    private boolean isFullscreen;
    private boolean fullscreenButtonPressed;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        isFullscreen = false;
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
        handleExit();
        handleFullscreen();
    }

    private void handleExit() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void handleFullscreen() {
        boolean fullscreenButtonWasPressed = fullscreenButtonPressed;
        fullscreenButtonPressed = Gdx.input.isKeyPressed(Input.Keys.F2);
        if (fullscreenButtonPressed && !fullscreenButtonWasPressed) {
            isFullscreen = !isFullscreen;
            if (isFullscreen) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            } else {
                Gdx.graphics.setWindowedMode(1280, 720);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
