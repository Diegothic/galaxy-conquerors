package com.galaxy.game.entity.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.collision.Collider;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.effects.ExplosionEffect;
import com.galaxy.game.level.GameLevel;

import java.util.Random;

public class Ship extends Entity {

    private final Texture texture;
    public final Sprite sprite;


    private static final float MOVEMENT_SPEED = 100.0f;

    private float maxOffset;
    private float changeDirectionTimer = 0f;
    private float maxDirectionTimer;
    private int movementDirection = 1;

    public boolean isAlive = true;
    private float respawnCounter = 0.0f;
    private float respawnDelay;

    private final Random random;

    public Ship(int sortingLayer) {
        super(sortingLayer);
        texture = new Texture(Gdx.files.internal("other/big_ship.png"));
        sprite = new Sprite(texture);
        random = new Random();
    }

    @Override
    public void onSpawn(GameLevel level) {
        super.onSpawn(level);
        Collider shipCollider = new Collider(this, new Vector2(32.0f, 16.0f));
        level.spawn(shipCollider);
        float levelWidth = getLevel().size.x;
        maxOffset = levelWidth;
        position.set(levelWidth / 2, 220);

        maxDirectionTimer = random.nextFloat() * 3.5f + .5f;

        respawnDelay = random.nextFloat() * 10.0f + 5.0f;
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        if (isAlive) {
            sprite.setPosition(
                    position.x - sprite.getWidth() / 2,
                    position.y - sprite.getHeight() / 2
            );
            sprite.draw(batch);
        }
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        if (isAlive) {
            changeDirectionTimer += delta;
            if (changeDirectionTimer >= maxDirectionTimer ||
                    position.x - sprite.getWidth() / 2 + MOVEMENT_SPEED * delta * movementDirection <= 0 ||
                    position.x + sprite.getWidth() / 2 + MOVEMENT_SPEED * delta * movementDirection >= maxOffset) {
                movementDirection *= -1;
                changeDirectionTimer = .0f;
                maxDirectionTimer = random.nextFloat() * 3.5f + .5f;
            }
            position.x += MOVEMENT_SPEED * delta * movementDirection;
        } else {
            respawnCounter += delta;
            if (respawnCounter >= respawnDelay) {
                respawnDelay = random.nextFloat() * 10.0f + 5.0f;
                respawnCounter = 0.0f;
                respawn();
            }
        }
    }

    @Override
    public void onDispose() {
        super.onDispose();
        texture.dispose();
    }

    private void respawn() {
        isAlive = true;
        float levelWidth = getLevel().size.x;
        position.set(levelWidth / 2, 220);
    }

    public void explode() {
        if (isAlive) {
            var explosion = new ExplosionEffect();
            explosion.position.set(position);
            explosion.rotation = random.nextFloat() * 360.0f;
            getLevel().spawn(explosion);
            isAlive = false;
        }
    }
}
