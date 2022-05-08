package com.galaxy.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.level.GameLevel;

public class Entity {

    private GameLevel level;
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
    }

    public void onSpawn(GameLevel level) {
        this.level = level;
        shouldBeRemoved = false;
    }

    public void onUpdate(float delta) {

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
        return shouldBeRemoved;
    }
}
