package com.galaxy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.galaxy.game.score.Scoreboard;
import com.galaxy.game.screen.MainMenuScreen;

public class GalaxyConquerors extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public Scoreboard scoreboard;
    public Music theme;

    private boolean isFullscreen;
    private boolean fullscreenButtonPressed;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        isFullscreen = false;
        var scoreboardDir = Gdx.files.local("scoreboard.json");
        scoreboard = new Scoreboard(scoreboardDir);
        theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/main_theme.wav"));
        theme.setLooping(true);
        theme.play();
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
        theme.dispose();
    }
}
