package com.galaxy.game.screen;

import com.badlogic.gdx.Screen;
import com.galaxy.game.GalaxyConquerors;
import com.galaxy.game.score.Scoreboard;

public class DevScreen implements Screen {

    private final GalaxyConquerors game;

    public DevScreen(GalaxyConquerors game) {
        this.game = game;
        Scoreboard scoreboard = new Scoreboard();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


    }

    @Override
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
