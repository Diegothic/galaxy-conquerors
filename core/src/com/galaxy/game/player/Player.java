package com.galaxy.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Player {
    private final Texture texture;
    private final Sprite sprite;
    private Vector2 position;
    private float rotation;

    private float speed;

    private final Vector2 bounds;

    private Texture flamesTexture;
    private Animation<TextureRegion> flamesAnimation;
    private float elapsedTime;
    private Sprite flamesSprite;

    private float hurtDuration;
    private final float hurtDurationBase;

    private Color color;

    private Texture gunTexture;
    private Sprite gunSprite;

    private Vector2[] shootingPoints;

    private Vector2 respawnPosition;
    private float respawnedDuration;
    private final float respawnedDurationBase;

    private Texture explosionTexture;
    private Animation<TextureRegion> explosionAnimation;
    private Sprite explosionSprite;
    private Vector2 explosionPosition;
    private float explosionRotation;
    private float explosionTime;
    private boolean explosion;

    private Texture projectileTexture;
    private Animation<TextureRegion> projectileAnimation;
    private Sprite projectileSprite;
    private Vector2 projectilePosition;
    private Vector2 projectileVelocity;
    private float projectileTime;
    private float shootingCd;
    private float shootingCdBase;

    public Player() {
        texture = new Texture(Gdx.files.internal("player/ship.png"));
        sprite = new Sprite(texture);

        position = new Vector2(0.0f, 0.0f);
        rotation = 0.0f;

        speed = 0.0f;

        bounds = new Vector2(Float.MIN_VALUE, Float.MAX_VALUE);

        flamesTexture = new Texture(Gdx.files.internal("player/ship_flames_sheet.png"));
        TextureRegion[][] tempFrames = TextureRegion.split(flamesTexture, 16, 16);
        TextureRegion[] flamesAnimationFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                flamesAnimationFrames[i] = tempFrames[j][i];
            }
        }
        flamesAnimation = new Animation<>(1f / 12f, flamesAnimationFrames);
        flamesSprite = new Sprite(flamesTexture, 16, 16);

        sprite.setColor(1.0f, 0.0f, 0.0f, 1.0f);

        hurtDuration = 0.0f;
        hurtDurationBase = 0.2f;

        color = new Color(1.0f, 1.0f, 1.0f, 1.0f);

        gunTexture = new Texture(Gdx.files.internal("player/gun_1.png"));
        gunSprite = new Sprite(gunTexture);

        shootingPoints = new Vector2[1];
        shootingPoints[0] = new Vector2(0.0f, 10.0f);

        respawnPosition = new Vector2(0.0f, 0.0f);
        respawnedDuration = 0.0f;
        respawnedDurationBase = 1.0f;

        explosionTexture = new Texture(Gdx.files.internal("other/explosion_sheet.png"));
        TextureRegion[][] tempFramesExplosion = TextureRegion.split(explosionTexture, 16, 16);
        TextureRegion[] explosionAnimationFrames = new TextureRegion[8];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (index < 8) {
                    explosionAnimationFrames[index] = tempFramesExplosion[i][j];
                }
                index++;
            }
        }
        explosionAnimation = new Animation<>(1f / 12f, explosionAnimationFrames);
        explosionSprite = new Sprite(explosionTexture, 16, 16);
        explosionTime = 0.0f;
        explosion = false;
        explosionPosition = new Vector2(0.0f, 0.0f);
        explosionRotation = 0.0f;

        projectileTexture = new Texture(Gdx.files.internal("player/player_projectile_sheet.png"));
        TextureRegion[][] tempFramesProjectile = TextureRegion.split(projectileTexture, 8, 8);
        TextureRegion[] projectileAnimationFrames = new TextureRegion[7];
        int indexProj = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (indexProj < 7) {
                    projectileAnimationFrames[indexProj] = tempFramesProjectile[i][j];
                }
                indexProj++;
            }
        }
        projectileAnimation = new Animation<>(1f / 12f, projectileAnimationFrames);
        projectileSprite = new Sprite(projectileTexture, 8, 8);
        projectilePosition = new Vector2(0.0f, 0.0f);
        projectileVelocity = new Vector2(0.0f, 200.0f);
        projectileTime = 0.0f;
        shootingCd = 0.0f;
        shootingCdBase = 1.0f;
    }

    public void update(float delta) {
        elapsedTime += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (speed >= 0.0f) {
                speed -= 400.0f * delta;
            } else if (speed < 0.0f) {
                speed -= 200.0f * delta;
            }
            rotation += delta * 50.0f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (speed <= 0.0f) {
                speed += 400.0f * delta;
            } else if (speed > 0.0f) {
                speed += 200.0f * delta;
            }
            rotation -= delta * 50.0f;
        } else {
            if (rotation > 0.0f) {
                rotation -= delta * 50.0f;
            }
            if (rotation < 0.0f) {
                rotation += delta * 50.0f;
            }
            if (speed > 1.0f) {
                speed -= delta * 100.0f;
            } else if (speed < -1.0f) {
                speed += delta * 100.0f;
            } else {
                speed = 0.0f;
            }
        }

        speed = Math.min(speed, 300.0f);
        speed = Math.max(speed, -300.0f);
        position.x += speed * delta;

        if (position.x - sprite.getWidth() / 2 <= bounds.x || position.x + sprite.getWidth() / 2 >= bounds.y) {
            speed *= -0.5f;
        }
        position.x = Math.min(Math.max(position.x, bounds.x + sprite.getWidth() / 2), bounds.y - sprite.getWidth() / 2);
        rotation = Math.max(Math.min(rotation, 10.0f), -10.0f);

        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            if (!explosion) {
                destroy();
            }
        }

        if (explosion) {
            explosionTime += delta;
            if (explosionAnimation.isAnimationFinished(explosionTime)) {
                explosion = false;
            }
        }

        if (hurtDuration > 0.0f) {
            hurtDuration -= delta;
        } else {
            hurtDuration = 0.0f;
        }

        if (respawnedDuration > 0.0f) {
            respawnedDuration -= delta;
        } else {
            respawnedDuration = 0.0f;
        }

        color.set(1.0f, 1.0f, 1.0f, 1.0f);

        if (respawnedDuration > 0.0f) {
            float respawnAmount = respawnedDuration / respawnedDurationBase;
            float respawnIntensity = (float) (((Math.sin(elapsedTime * 100.0f) + 1.0f) / 2.0f)) * respawnAmount;
            color.set(1.0f - respawnIntensity, 1.0f - respawnIntensity, 1.0f, 1.0f - respawnAmount);
        }
        if (hurtDuration > 0.0f) {
            float hurtAmount = hurtDuration / hurtDurationBase;
            color.set(1.0f, 1.0f - hurtAmount, 1.0f - hurtAmount, 1.0f);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(shootingCd <= 0.0f){
                shootingCd = shootingCdBase;
                projectilePosition.set(position.x + shootingPoints[0].x, position.y + shootingPoints[0].y);
                projectileTime = 0.0f;
            }
        }

        if(shootingCd > 0.0f){
            projectileTime += delta;
            shootingCd -= delta;
            projectilePosition.add(projectileVelocity.x * delta, projectileVelocity.y * delta);
        }
    }

    public void render(SpriteBatch batch) {
        if(shootingCd > 0.0f){
            projectileSprite.setRegion(projectileAnimation.getKeyFrame(projectileTime,true));
            projectileSprite.setPosition(projectilePosition.x - projectileSprite.getWidth() / 2, projectilePosition.y - projectileSprite.getHeight() /2);
            projectileSprite.draw(batch);
        }

        flamesSprite.setRegion(flamesAnimation.getKeyFrame(elapsedTime, true));
        flamesSprite.setPosition(position.x - flamesSprite.getWidth() / 2, position.y - flamesSprite.getHeight() / 2);
        flamesSprite.setRotation(rotation);
        flamesSprite.setColor(color);
        flamesSprite.draw(batch);

        gunSprite.setPosition(position.x + 0.0f - gunSprite.getWidth() / 2, position.y + 9.0f - gunSprite.getHeight() / 2);
        gunSprite.setColor(color);
        gunSprite.draw(batch);

        sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);
        sprite.setRotation(rotation);
        sprite.setColor(color);
        sprite.draw(batch);

        if (explosion) {
            explosionSprite.setRegion(explosionAnimation.getKeyFrame(explosionTime, false));
            explosionSprite.setPosition(explosionPosition.x - explosionSprite.getWidth() / 2, explosionPosition.y - explosionSprite.getHeight() / 2);
            explosionSprite.setRotation(explosionRotation);
            explosionSprite.draw(batch);
        }
    }

    public void dispose() {
        texture.dispose();
        flamesTexture.dispose();
        gunTexture.dispose();
        explosionTexture.dispose();
    }

    public void setBounds(Vector2 newBounds) {
        bounds.set(newBounds);
    }

    public void setPosition(Vector2 newPosition) {
        position.set(newPosition);
    }

    public void destroy() {
        explosionPosition.set(position);
        Random r = new Random();
        explosionRotation = r.nextFloat() * 360.0f;
        explosionTime = 0.0f;
        explosion = true;
        respawn();
    }

    public void setRespawnPosition(Vector2 newPosition) {
        respawnPosition.set(newPosition);
    }

    public void respawn() {
        respawnedDuration = respawnedDurationBase;
        position.set(respawnPosition);
        speed = 0.0f;
    }
}
