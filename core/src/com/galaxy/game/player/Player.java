package com.galaxy.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.graphics.AnimatedSprite;

import java.util.Random;

public class Player {

    private final Texture texture;
    public final Sprite sprite;
    public final Vector2 position;
    public float rotation;
    private final Color color;

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

    private final AnimatedSprite explosionSprite;
    private final Vector2 explosionPosition;
    private float explosionRotation;
    private boolean isExploding;

    private float elapsedTime;
    private final Random random;

    public Player() {
        texture = new Texture(Gdx.files.internal("player/ship.png"));
        sprite = new Sprite(texture);
        position = new Vector2(0.0f, 0.0f);
        rotation = 0.0f;
        color = new Color(1.0f, 1.0f, 1.0f, 1.0f);

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

        explosionSprite = new AnimatedSprite("other/explosion_sheet.png",
                16, 16, 8,
                1.0f / 12.0f
        );
        explosionPosition = new Vector2(0.0f, 0.0f);
        explosionRotation = 0.0f;
        isExploding = false;

        elapsedTime = 0.0f;
        random = new Random();
    }

    public void update(final float delta) {
        elapsedTime += delta;
        movement.update(delta);
        flamesSprite.step(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.H) && !isExploding) {
            destroy();
        }

        if (isExploding) {
            explosionSprite.step(delta);
            if (explosionSprite.isAnimationFinished()) {
                isExploding = false;
            }
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

    public void render(SpriteBatch batch) {
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

        if (isExploding) {
            explosionSprite.setPosition(
                    explosionPosition.x - explosionSprite.getWidth() / 2,
                    explosionPosition.y - explosionSprite.getHeight() / 2
            );
            explosionSprite.setRotation(explosionRotation);
            explosionSprite.draw(batch);
        }

        if (shootingCd > 0.0f && !muzzleFlashSprite.isAnimationFinished()) {
            muzzleFlashSprite.setPosition(
                    position.x + shootingPoint.x - muzzleFlashSprite.getWidth() / 2,
                    position.y + shootingPoint.y - muzzleFlashSprite.getHeight() / 2
            );
            muzzleFlashSprite.draw(batch);
        }
    }

    public void dispose() {
        texture.dispose();
        flamesSprite.dispose();
        gunTexture.dispose();
        explosionSprite.dispose();
        projectileSprite.dispose();
        muzzleFlashSprite.dispose();
    }

    public void setBounds(Vector2 newBounds) {
        bounds.set(newBounds);
    }

    public void destroy() {
        explosionPosition.set(position);
        explosionRotation = random.nextFloat() * 360.0f;
        explosionSprite.resetTimer();
        isExploding = true;
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
