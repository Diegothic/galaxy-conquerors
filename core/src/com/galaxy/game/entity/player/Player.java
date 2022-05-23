package com.galaxy.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.collision.Collider;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.SortingLayer;
import com.galaxy.game.entity.effects.ExplosionEffect;
import com.galaxy.game.graphics.AnimatedSprite;
import com.galaxy.game.level.GameLevel;

import java.util.Random;

public class Player extends Entity {

    private final Texture texture;
    public final Sprite sprite;

    public final Vector2 bounds;
    private final PlayerMovement movement;

    private final AnimatedSprite flamesSprite;

    private final Texture gunTexture;
    private final Sprite gunSprite;
    private final PlayerShooting shooting;

    private final Vector2 respawnPosition;
    private float respawnedDuration;
    private final float respawnedDurationBase;

    private float elapsedTime;
    private final Random random;

    private final Sound respawnSound;

    public Player() {
        super(SortingLayer.PLAYER);
        texture = new Texture(Gdx.files.internal("player/ship.png"));
        sprite = new Sprite(texture);

        movement = new PlayerMovement(this);
        bounds = new Vector2(Float.MIN_VALUE, Float.MAX_VALUE);

        flamesSprite = new AnimatedSprite("player/ship_flames_sheet.png",
                16, 16, 4,
                1.0f / 12.0f
        );
        flamesSprite.setLooping(true);

        gunTexture = new Texture(Gdx.files.internal("player/gun_1.png"));
        gunSprite = new Sprite(gunTexture);

        shooting = new PlayerShooting(this, new Vector2(0.0f, 10.0f), 1.0f);

        respawnPosition = new Vector2(0.0f, 0.0f);
        respawnedDuration = 0.0f;
        respawnedDurationBase = 1.0f;

        elapsedTime = 0.0f;
        random = new Random();

        respawnSound = Gdx.audio.newSound(Gdx.files.internal("sounds/player_respawn.wav"));
    }

    @Override
    public void onSpawn(GameLevel level) {
        super.onSpawn(level);
        float levelWidth = getLevel().size.x;
        position.set(levelWidth / 2, 16);
        setBounds(0, levelWidth);
        setRespawnPosition(new Vector2(levelWidth / 2, 16));
        respawn();
        var collider = new Collider(this, new Vector2(15.0f, 15.0f));
        getLevel().spawn(collider);
    }

    @Override
    public void onUpdate(final float delta) {
        super.onUpdate(delta);
        elapsedTime += delta;
        movement.update(delta);
        shooting.update(delta);
        flamesSprite.step(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            explode();
        }

        color.set(1.0f, 1.0f, 1.0f, 1.0f);

        if (respawnedDuration > 0.0f) {
            respawnedDuration -= delta;
            float respawnAmount = respawnedDuration / respawnedDurationBase;
            float respawnIntensity = (float) (((Math.sin(elapsedTime * 100.0f) + 1.0f) / 2.0f)) * respawnAmount;
            color.set(1.0f - respawnIntensity, 1.0f - respawnIntensity, 1.0f, 1.0f - respawnAmount);
        }
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        flamesSprite.setPosition(
                position.x - flamesSprite.getWidth() / 2,
                position.y - flamesSprite.getHeight() / 2
        );
        flamesSprite.setRotation(rotation);
        flamesSprite.setColor(color);
        flamesSprite.draw(batch);

        gunSprite.setPosition(
                position.x + 0.0f - gunSprite.getWidth() / 2,
                position.y + 9.0f - gunSprite.getHeight() / 2
        );
        gunSprite.setColor(color);
        gunSprite.draw(batch);

        sprite.setPosition(
                position.x - sprite.getWidth() / 2,
                position.y - sprite.getHeight() / 2
        );
        sprite.setRotation(rotation);
        sprite.setColor(color);
        sprite.draw(batch);
    }

    @Override
    public void onDispose() {
        super.onDispose();
        texture.dispose();
        flamesSprite.dispose();
        gunTexture.dispose();
        shooting.dispose();
    }

    public void setBounds(float min, float max) {
        bounds.set(min, max);
    }

    public void explode() {
        if (respawnedDuration > 0.0f) {
            return;
        }
        var explosion = new ExplosionEffect();
        explosion.position.set(position);
        explosion.rotation = random.nextFloat() * 360.0f;
        getLevel().spawn(explosion);

        getLevel().getGameMode().decreaseLives();
        if (!getLevel().getGameMode().isLost()) {
            respawn();
        } else {
            getLevel().destroy(this);
        }
    }

    public void setRespawnPosition(Vector2 newPosition) {
        respawnPosition.set(newPosition);
    }

    public void respawn() {
        respawnSound.play();
        respawnedDuration = respawnedDurationBase;
        position.set(respawnPosition);
        movement.reset();
    }
}
