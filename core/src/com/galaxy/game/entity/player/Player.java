package com.galaxy.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.effects.Explosion;
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
    private final Vector2 shootingPoint;

    private final AnimatedSprite muzzleFlashSprite;
    private final AnimatedSprite projectileSprite;
    private final Vector2 projectilePosition;
    private final Vector2 projectileVelocity;
    private float shootingCd;
    private final float shootingCdBase;

    private final Vector2 respawnPosition;
    private float respawnedDuration;
    private final float respawnedDurationBase;

    private float elapsedTime;
    private final Random random;

    public Player(int sortingLayer) {
        super(sortingLayer);
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

        shootingPoint = new Vector2(0.0f, 10.0f);

        muzzleFlashSprite = new AnimatedSprite("player/player_projectile_shoot_sheet.png",
                16, 16, 6,
                1.0f / 24.0f
        );
        projectileSprite = new AnimatedSprite("player/player_projectile_sheet.png",
                8, 8, 7,
                1.0f / 12.0f
        );
        projectileSprite.setLooping(true);
        projectilePosition = new Vector2(0.0f, 0.0f);
        projectileVelocity = new Vector2(0.0f, 200.0f);
        shootingCd = 0.0f;
        shootingCdBase = 1.0f;

        respawnPosition = new Vector2(0.0f, 0.0f);
        respawnedDuration = 0.0f;
        respawnedDurationBase = 1.0f;

        elapsedTime = 0.0f;
        random = new Random();
    }

    @Override
    public void onSpawn(GameLevel level) {
        super.onSpawn(level);
        float levelWidth = getLevel().size.x;
        position.set(levelWidth / 2, 16);
        setBounds(0, levelWidth);
        setRespawnPosition(new Vector2(levelWidth / 2, 16));
        respawn();
    }

    @Override
    public void onUpdate(final float delta) {
        super.onUpdate(delta);
        elapsedTime += delta;
        movement.update(delta);
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

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootingCd <= 0.0f) {
            shootingCd = shootingCdBase;
            projectilePosition.set(position.x + shootingPoint.x, position.y + shootingPoint.y);
            projectileSprite.resetTimer();
            muzzleFlashSprite.resetTimer();
        }

        if (shootingCd > 0.0f) {
            shootingCd -= delta;
            projectileSprite.step(delta);
            muzzleFlashSprite.step(delta);
            projectilePosition.add(projectileVelocity.x * delta, projectileVelocity.y * delta);
        }
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        if (shootingCd > 0.0f) {
            projectileSprite.setPosition(
                    projectilePosition.x - projectileSprite.getWidth() / 2,
                    projectilePosition.y - projectileSprite.getHeight() / 2
            );
            projectileSprite.draw(batch);
        }

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

        if (shootingCd > 0.0f && !muzzleFlashSprite.isAnimationFinished()) {
            muzzleFlashSprite.setPosition(
                    position.x + shootingPoint.x - muzzleFlashSprite.getWidth() / 2,
                    position.y + shootingPoint.y - muzzleFlashSprite.getHeight() / 2
            );
            muzzleFlashSprite.draw(batch);
        }
    }

    @Override
    public void onDispose() {
        super.onDispose();
        texture.dispose();
        flamesSprite.dispose();
        gunTexture.dispose();
        projectileSprite.dispose();
        muzzleFlashSprite.dispose();
    }

    public void setBounds(float min, float max) {
        bounds.set(min, max);
    }

    public void explode() {
        Explosion explosion = new Explosion(999);
        explosion.position.set(position);
        explosion.rotation = random.nextFloat() * 360.0f;
        getLevel().spawn(explosion);
        respawn();
    }

    public void setRespawnPosition(Vector2 newPosition) {
        respawnPosition.set(newPosition);
    }

    public void respawn() {
        respawnedDuration = respawnedDurationBase;
        position.set(respawnPosition);
        movement.reset();
    }
}
