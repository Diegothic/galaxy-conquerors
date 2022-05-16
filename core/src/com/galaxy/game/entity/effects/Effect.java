package com.galaxy.game.entity.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.SortingLayer;
import com.galaxy.game.graphics.AnimatedSprite;
import com.galaxy.game.level.GameLevel;

import java.util.Random;

public abstract class Effect extends Entity {

    public final AnimatedSprite sprite;

    private Sound sound;
    private boolean randomizePitch;

    private Random random;

    public Effect(String spriteSheetPath, int tileWidth, int tileHeight, int tileCount, float frameDuration) {
        super(SortingLayer.EFFECTS);
        sprite = new AnimatedSprite(spriteSheetPath,
                tileWidth,
                tileHeight,
                tileCount,
                frameDuration
        );

        sound = null;
        randomizePitch = false;

        random = new Random();
    }

    protected void addSound(String soundPath, boolean randomizePitch) {
        sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
        this.randomizePitch = randomizePitch;
    }

    @Override
    public void onSpawn(GameLevel level) {
        super.onSpawn(level);
        sprite.resetTimer();
        if (sound != null) {
            float pitch = 1.0f;
            if (randomizePitch) {
                pitch += (random.nextFloat() * 0.2f) - 0.1f;
            }
            sound.play(1.0f, pitch, 0.0f);
        }
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
        if (sound != null) {
            sound.dispose();
        }
    }
}
