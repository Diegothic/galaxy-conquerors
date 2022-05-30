package com.galaxy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.galaxy.game.score.Score;
import com.galaxy.game.score.Scoreboard;
import com.galaxy.game.screen.GameScreen;
import com.galaxy.game.screen.MainMenuScreen;
import com.galaxy.game.screen.ScoreboardScreen;
import com.galaxy.game.util.GameState;

import static com.galaxy.game.util.GameState.SCOREBOARD;

public class GalaxyConquerors extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    public static GameState gameState;

    FileHandle scoreboardDir;
    Scoreboard scoreboard;

    private Music theme;
    private boolean isFullscreen;
    private boolean fullscreenButtonPressed;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        isFullscreen = false;
        scoreboardDir = Gdx.files.local("scoreboard.json");
        scoreboard = new Scoreboard(scoreboardDir);
        gameState = GameState.MENU;
        theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/main_theme.wav"));
        theme.setLooping(true);
        theme.play();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
        switch (gameState) {
            case MENU:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                    this.setScreen(new GameScreen(this));
                    gameState = GameState.IN_GAME;
                }
                break;
            case GAME_OVER:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                    scoreboard.addScore(Score.getPoints());
                    this.setScreen(new ScoreboardScreen(this, scoreboard));
                    gameState = SCOREBOARD;

                }
                break;
            case SCOREBOARD:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                    theme.stop();
                    this.setScreen(new GameScreen(this));
                    gameState = GameState.IN_GAME;
                }
                theme.play();
                break;
        }

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
