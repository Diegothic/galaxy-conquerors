package com.galaxy.game.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.SortingLayer;

import java.util.function.Consumer;

public class Collider extends Entity {

    private final Rectangle box;
    private Consumer<Collider> onCollisionFunction;
    public boolean enabled;

    public Collider(Entity parent, Vector2 size, Vector2 offset) {
        super(SortingLayer.COLLISION);
        if (parent != null) {
            attachTo(parent, offset);
            position.set(parent.position);
        }
        box = new Rectangle();
        box.setWidth(size.x);
        box.setHeight(size.y);
        box.setPosition(position.x + offset.x, position.y + offset.y);
        enabled = true;
    }

    public Collider(Entity parent, Vector2 size) {
        this(parent, size, new Vector2());
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        box.setPosition(position.x - box.getWidth() / 2.0f, position.y - box.getHeight() / 2.0f);
    }

    @Override
    public void onDebugRender(ShapeRenderer renderer) {
        super.onDebugRender(renderer);
        renderer.setColor(enabled ? Color.GREEN : Color.RED);
        renderer.rect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDispose() {
        super.onDispose();
    }

    public boolean overlaps(Collider other) {
        return box.overlaps(other.box);
    }

    public void setOnCollision(Consumer<Collider> onCollisionFunction) {
        this.onCollisionFunction = onCollisionFunction;
    }

    public void onCollision(Collider other) {
        if (!enabled || !other.enabled || onCollisionFunction == null) {
            return;
        }
        onCollisionFunction.accept(other);
    }
}
