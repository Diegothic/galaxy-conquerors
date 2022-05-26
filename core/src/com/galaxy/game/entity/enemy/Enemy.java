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

    private static final int MAX_OFFSET = 154;
    private static final float MOVEMENT_CD = 0.2f;

    private int currentOffset = 0;
    private float movementTimer = 0f;
    private final Vector2 velocity;
    private int movementDirection = 1;
    private int numberOfStepsDown = 0;

    private float SHOOTING_CHANCE = 0.15f;
    private float shootingCD;
    private float shootingTimer = 0f;
    private final Vector2 shootingOffset;
    private final int enemyValue;

    private final Random random;

    public Enemy(int x, int y, int enemyValue, String pathToSprite) {
        super(SortingLayer.ENEMIES);
        this.enemyValue = (enemyValue + 1) * 50;
        position.x = x;
        position.y = y;
        texture = new Texture(Gdx.files.internal(pathToSprite));
        sprite = new Sprite(texture);
        velocity = new Vector2(2, 15);

        random = new Random();

        shootingCD = random.nextFloat() * 3f + .2f;
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
        if (movementTimer >= MOVEMENT_CD) {
            movementTimer = 0f;
            currentOffset += velocity.x;
            int isMovingDown = 0;
            if (currentOffset >= MAX_OFFSET || currentOffset <= 0) {
                currentOffset = 0;
                movementDirection *= -1;
                numberOfStepsDown++;
                int maxNumberOfStepsDown = 4;
                if (numberOfStepsDown <= maxNumberOfStepsDown) {
                    isMovingDown = -1;
                    switch (numberOfStepsDown){
                        case 1:
                        case 3:
                            velocity.x++;
                            break;
                        case 2:
                            SHOOTING_CHANCE *= 2;
                            break;
                        case 4:
                            velocity.x = 6;
                            SHOOTING_CHANCE *= 2;
                            break;
                    }
                }
            }
            position.x += movementDirection * velocity.x;
            position.y += isMovingDown * velocity.y;
        }

        if (shootingTimer >= shootingCD) {
            shootingCD = random.nextFloat() * 3f + .2f;
            shootingTimer = 0;
            float chance = random.nextFloat();
            if (chance <= SHOOTING_CHANCE) {
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

    public int getEnemyValue(){
        return enemyValue;
    }
}
