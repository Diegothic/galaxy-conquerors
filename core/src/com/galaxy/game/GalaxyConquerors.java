package com.galaxy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.galaxy.game.screen.GameScreen;
import com.galaxy.game.screen.MainMenuScreen;

import java.security.Key;

public class GalaxyConquerors extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    private boolean isMainMenu = true;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        this.setScreen(new MainMenuScreen(this));

    }

    @Override
    public void render() {
        super.render();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && isMainMenu){
            isMainMenu = false;
            this.screen.dispose();
            this.setScreen(new GameScreen(this));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
