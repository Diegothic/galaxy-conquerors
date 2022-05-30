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
import com.galaxy.game.util.Font;

public class MainMenuScreen implements Screen {

    private static final int VIEWPORT_WIDTH = 426;
    private static final int VIEWPORT_HEIGHT = 240;

    private final GalaxyConquerors game;
    private final Font title;
    private final Font startText;
    private final OrthographicCamera camera;

    private final Texture backgroundTexture;
    private final Sprite background;
    private float elapsed;

    public MainMenuScreen(GalaxyConquerors game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        title = new Font(25);
        startText = new Font(10);

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
        title.printText(game.batch, "GALAXY CONQUERORS", new Vector2(50, 150));
        startText.printText(game.batch, "PRESS ANY KEY TO START", new Vector2(120, 100));
        game.batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen(game));
        }
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
        backgroundTexture.dispose();
    }
}
