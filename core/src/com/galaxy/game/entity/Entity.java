package com.galaxy.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.level.GameLevel;

public abstract class Entity {

    private GameLevel level;
    private Entity parent;
    private final Vector2 offset;
    private final int sortingLayer;
    private boolean shouldBeRemoved;

    public final Vector2 position;
    public float rotation;
    public final Color color;

    public Entity(int sortingLayer) {
        this.sortingLayer = sortingLayer;
        shouldBeRemoved = false;
        position = new Vector2(0.0f, 0.0f);
        rotation = 0.0f;
        color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        level = null;
        parent = null;
        offset = new Vector2();
    }

    public void onSpawn(GameLevel level) {
        this.level = level;
        shouldBeRemoved = false;
    }

    public void onUpdate(float delta) {
        if (parent != null) {
            position.set(
                    parent.position.x + offset.x,
                    parent.position.y + offset.y
            );
            if (parent.removalRequested()) {
                parent = null;
            }
        }
    }

    public void onRender(SpriteBatch batch) {

    }

    public void onDebugRender(ShapeRenderer renderer) {

    }

    public void onDispose() {
        this.level = null;
    }

    public void onDestroy() {
        shouldBeRemoved = true;
    }

    public GameLevel getLevel() {
        return level;
    }

    public int getSortingLayer() {
        return sortingLayer;
    }

    public boolean removalRequested() {
        if (parent != null && parent.removalRequested()) {
            return true;
        }
        return shouldBeRemoved;
    }

    public void attachTo(Entity parent, Vector2 offset) {
        if (parent == null || parent == this) {
            return;
        }
        this.offset.set(offset);
        this.parent = parent;
    }

    public Entity getParent() {
        return parent;
    }
}
