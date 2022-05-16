package com.galaxy.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.collision.Collider;
import com.galaxy.game.entity.effects.ExplosionEffect;
import com.galaxy.game.graphics.AnimatedSprite;
import com.galaxy.game.level.GameLevel;

import java.util.Random;

public class Shield extends Entity {

    private final AnimatedSprite sprite;

    private final AnimatedSprite barSprite;

    private float health;
    private final float maxHealth;
    private float damagedCd;
    private final float damagedCdBase;

    public Shield(float maxHealth) {
        super(SortingLayer.PLAYER);
        sprite = new AnimatedSprite("shield/shield_sheet.png",
                32, 16, 4,
                1.0f / 12.0f
        );
        sprite.setLooping(true);

        barSprite = new AnimatedSprite("shield/shield_bar_sheet.png",
                16, 16, 13,
                1.0f / 13.0f
        );

        this.maxHealth = maxHealth;
        health = maxHealth;
        damagedCd = 0.0f;
        damagedCdBase = 0.3f;
    }

    @Override
    public void onSpawn(GameLevel level) {
        super.onSpawn(level);
        var collider = new Collider(this, new Vector2(32.0f, 10.0f));
        getLevel().spawn(collider);
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        sprite.step(delta);

        color.set(1.0f, 1.0f, 1.0f, 1.0f);
        if (damagedCd > 0.0f) {
            float percent = damagedCd / damagedCdBase;
            color.set(1.0f, 1.0f - percent, 1.0f - percent, 1.0f);
            damagedCd -= delta;
        }
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        sprite.setPosition(
                position.x - sprite.getWidth() / 2.0f,
                position.y - sprite.getHeight() / 2.0f
        );
        sprite.setColor(color);
        sprite.draw(batch);
        barSprite.setPosition(
                position.x - barSprite.getWidth() / 2.0f,
                position.y - barSprite.getHeight() / 2.0f - 1.0f
        );
        barSprite.draw(batch);
    }

    @Override
    public void onDispose() {
        super.onDispose();
        sprite.dispose();
        barSprite.dispose();
    }

    public void damage(float amount) {
        damagedCd = damagedCdBase;
        health -= amount;
        float percent = health / maxHealth;
        barSprite.resetTimer();
        barSprite.step(1.0f - percent);
        if (health <= 0.0f) {
            health = 0.0f;
            explode();
            getLevel().destroy(this);
        }
    }

    private void explode() {
        var random = new Random();
        var explosion = new ExplosionEffect();
        explosion.position.set(position);
        explosion.rotation = random.nextFloat() * 360.0f;
        getLevel().spawn(explosion);
    }
}
