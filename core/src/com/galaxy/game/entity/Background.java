package com.galaxy.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background extends Entity {

    private final Texture texture;
    public final Sprite sprite;

    private float elapsed;

    public Background() {
        super(SortingLayer.BACKGROUND);
        texture = new Texture(Gdx.files.internal("other/background_galaxy.png"));
        sprite = new Sprite(texture);
        elapsed = 0.0f;
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        elapsed += delta;
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        sprite.setColor(1.0f + ((float) Math.sin(elapsed * 0.5f) * 0.1f - 0.1f), 1.0f, 1.0f, 1.0f);
        sprite.setPosition(
                position.x - sprite.getWidth() / 2.0f,
                position.y - sprite.getHeight() / 2.0f
        );
        sprite.draw(batch);
    }

    @Override
    public void onDispose() {
        super.onDispose();
        texture.dispose();
    }
}
