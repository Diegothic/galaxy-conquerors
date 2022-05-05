package com.galaxy.game.entity.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.graphics.AnimatedSprite;
import com.galaxy.game.level.GameLevel;

public class Explosion extends Entity {

    private final AnimatedSprite sprite;

    public Explosion(int sortingLayer) {
        super(sortingLayer);
        sprite = new AnimatedSprite("other/explosion_sheet.png",
                16, 16, 8,
                1.0f / 12.0f
        );
    }

    @Override
    public void onSpawn(GameLevel level) {
        super.onSpawn(level);
        sprite.resetTimer();
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        if (sprite.isAnimationFinished()) {
            getLevel().destroy(this);
        } else {
            sprite.step(delta);
        }
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        sprite.setPosition(
                position.x - sprite.getWidth() / 2,
                position.y - sprite.getHeight() / 2
        );
        sprite.setRotation(rotation);
        sprite.draw(batch);
    }

    @Override
    public void onDispose() {
        super.onDispose();
        sprite.dispose();
    }
}
