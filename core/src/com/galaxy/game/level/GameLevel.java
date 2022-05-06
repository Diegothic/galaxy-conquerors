package com.galaxy.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameLevel {

    private static final int INIT_ENTITY_CAPACITY = 128;

    public final Vector2 size;
    private final List<Entity> entities;
    private final List<Entity> entitiesToSpawn;
    private final List<Entity> spawnedEntities;

    public GameLevel(int width, int height) {
        this.size = new Vector2(width, height);
        entities = new ArrayList<>(INIT_ENTITY_CAPACITY);
        entitiesToSpawn = new ArrayList<>(INIT_ENTITY_CAPACITY);
        spawnedEntities = new ArrayList<>(INIT_ENTITY_CAPACITY);
    }

    public void start() {
        initLevel();
        handleSpawnRequests();
    }

    protected void initLevel() {

    }

    public void update(float delta) {
        handleSpawnRequests();
        handleRemoveRequests();
        entities.forEach(entity -> entity.onUpdate(delta));
    }

    private void handleSpawnRequests() {
        spawnedEntities.addAll(entitiesToSpawn);
        entitiesToSpawn.clear();
        spawnedEntities.forEach(entity -> entity.onSpawn(this));
        entities.addAll(spawnedEntities);
        spawnedEntities.clear();
    }

    private void handleRemoveRequests() {
        entities.stream().filter(Entity::removalRequested).forEach(Entity::onDispose);
        entities.removeIf(Entity::removalRequested);
    }

    public void render(SpriteBatch batch) {
        entities.stream()
                .sorted(Comparator.comparingInt(Entity::getSortingLayer))
                .forEach(entity -> entity.onRender(batch));
    }

    public void dispose() {
        entities.forEach(Entity::onDispose);
    }

    public void spawn(Entity entity) {
        if (entities.contains(entity) || entitiesToSpawn.contains(entity)) {
            return;
        }
        entitiesToSpawn.add(entity);
    }

    public void destroy(Entity entity) {
        if (!entities.contains(entity)) {
            return;
        }
        entity.onDestroy();
    }
}
