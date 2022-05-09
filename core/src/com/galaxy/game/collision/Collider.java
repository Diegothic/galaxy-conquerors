package com.galaxy.game.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.Entity;
import com.galaxy.game.entity.SortingLayer;

import java.util.function.Consumer;

public class Collider extends Entity {

    private final Entity parent;
    private final Rectangle box;
    private final Vector2 offset;
    private Consumer<Collider> onCollisionFunction;

    public boolean enabled;

    public Collider(Entity parent, float width, float height, float offsetX, float offsetY) {
        super(SortingLayer.COLLISION);
        this.parent = parent;
        if (parent != null) {
            position.set(parent.position);
        }
        offset = new Vector2(offsetX, offsetY);
        box = new Rectangle();
        box.setWidth(width);
        box.setHeight(height);
        box.setPosition(position.x + offset.x, position.y + offset.y);
        enabled = true;
    }

    public Collider(Entity parent, float width, float height) {
        this(parent, width, height, 0.0f, 0.0f);
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        if (parent != null) {
            position.set(parent.position.x - box.getWidth() / 2.0f, parent.position.y - box.getHeight() / 2.0f);
        }
        box.setPosition(position.x + offset.x, position.y + offset.y);
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
        enabled = false;
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

    public Entity getParent() {
        return parent;
    }

}
