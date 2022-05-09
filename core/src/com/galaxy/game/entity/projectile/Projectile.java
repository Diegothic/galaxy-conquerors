package com.galaxy.game.entity.projectile;

import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.collision.Collider;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.SortingLayer;
import com.galaxy.game.level.GameLevel;

import java.util.function.Consumer;

public class Projectile extends Entity {

    private final Vector2 direction;
    private final float speed;
    private final Collider collider;
    private float lifetime;

    public Projectile(float directionX, float directionY, float speed, float width, float height, float lifetime) {
        super(SortingLayer.PROJECTILES);
        this.direction = new Vector2(directionX, directionY);
        this.speed = speed;
        this.collider = new Collider(this, width, height);
        this.lifetime = lifetime;
    }

    @Override
    public void onSpawn(GameLevel level) {
        super.onSpawn(level);
        level.spawn(collider);
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        decreaseLifetime(delta);
        move(delta);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLevel().destroy(collider);
    }

    protected void decreaseLifetime(float amount) {
        lifetime -= amount;
        if (lifetime <= 0.0f) {
            getLevel().destroy(this);
        }
    }

    protected void move(float amount) {
        position.x += direction.x * speed * amount;
        position.y += direction.y * speed * amount;
    }

    protected void setOnCollision(Consumer<Collider> onCollisionFunction) {
        collider.setOnCollision(onCollisionFunction);
    }
}
