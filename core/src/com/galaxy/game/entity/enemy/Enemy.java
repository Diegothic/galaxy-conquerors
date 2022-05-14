package com.galaxy.game.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.collision.Collider;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.SortingLayer;
import com.galaxy.game.entity.effects.EnemyExplosionEffect;
import com.galaxy.game.entity.effects.EnemyMuzzleFlashEffect;
import com.galaxy.game.entity.projectile.EnemyProjectile;
import com.galaxy.game.level.GameLevel;

import java.util.Random;

public class Enemy extends Entity {
    private final Texture texture;
    public final Sprite sprite;

    private final int maxOffset = 154;
    private int currentOffset = 0;
    private float movementCd = 0.2f;
    private float movementTimer = 0f;
    private Vector2 velocity;
    private int movementDirection = 1;
    private int numberOfStepsDown = 0;

    private float shootingCD;
    private float shootingTimer = 0f;
    private final float shootingChance = 0.1f;
    private final Vector2 shootingOffset;

    private final Random random;

    public Enemy(int x, int y, String pathToSprate) {
        super(SortingLayer.ENEMIES);
        position.x = x;
        position.y = y;
        texture = new Texture(Gdx.files.internal(pathToSprate));
        sprite = new Sprite(texture);
        velocity = new Vector2(2, 15);

        random = new Random();

        shootingCD = random.nextFloat() * 3f + 1f;
        shootingOffset = new Vector2(0.0f, -8.0f);
    }

    @Override
    public void onSpawn(GameLevel level) {
        super.onSpawn(level);
        Collider enemyCollider = new Collider(this, new Vector2(16.0f, 16.0f));
        level.spawn(enemyCollider);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        sprite.setPosition(
                position.x - sprite.getWidth() / 2,
                position.y - sprite.getHeight() / 2
        );
        sprite.draw(batch);
    }

    @Override
    public void onUpdate(float delta) {
        movementTimer += delta;
        shootingTimer += delta;
        if (movementTimer >= movementCd) {
            movementTimer = 0f;
            currentOffset += velocity.x;
            int isMovingDown = 0;
            if (currentOffset == maxOffset || currentOffset == 0) {
                currentOffset = 0;
                movementDirection *= -1;
                numberOfStepsDown++;
                int maxNumberOfStepsDown = 4;
                if (numberOfStepsDown <= maxNumberOfStepsDown) {
                    isMovingDown = -1;
                }
            }
            position.x += movementDirection * velocity.x;
            position.y += isMovingDown * velocity.y;
        }

        if (shootingTimer >= shootingCD) {
            shootingCD = random.nextFloat() * 3f + 1f;
            shootingTimer = 0;
            float chance = random.nextFloat();
            if (chance <= shootingChance) {
                shoot();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        var explosion = new EnemyExplosionEffect();
        explosion.position.set(position);
        explosion.rotation = random.nextFloat() * 360.0f;
        getLevel().spawn(explosion);
    }

    private void shoot() {
        var muzzleFlash = new EnemyMuzzleFlashEffect(this, shootingOffset);
        getLevel().spawn(muzzleFlash);
        var projectile = new EnemyProjectile();
        projectile.position.set(
                position.x + shootingOffset.x,
                position.y + shootingOffset.y
        );
        getLevel().spawn(projectile);
    }
}
