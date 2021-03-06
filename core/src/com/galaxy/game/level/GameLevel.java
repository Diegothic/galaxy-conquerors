package com.galaxy.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.collision.Collider;
import com.galaxy.game.entity.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class GameLevel {

    private static final int INIT_ENTITY_CAPACITY = 128;
    private static final int MAX_LIVES = 3;

    public final Vector2 size;
    private final List<Entity> entities;
    private final List<Entity> entitiesToSpawn;
    private final List<Entity> spawnedEntities;
    private final List<Collider> colliders;

    private final GameMode gameMode;

    public GameLevel(int width, int height) {
        this.size = new Vector2(width, height);
        entities = new ArrayList<>(INIT_ENTITY_CAPACITY);
        entitiesToSpawn = new ArrayList<>(INIT_ENTITY_CAPACITY);
        spawnedEntities = new ArrayList<>(INIT_ENTITY_CAPACITY);
        colliders = new ArrayList<>(INIT_ENTITY_CAPACITY);

        gameMode = new GameMode(MAX_LIVES);
    }

    public final void start() {
        initLevel();
        handleSpawnRequests();
    }

    public final void end() {
        endLevel();
    }

    protected abstract void initLevel();

    protected abstract void endLevel();

    public void update(float delta) {
        handleSpawnRequests();
        handleRemoveRequests();
        entities.forEach(entity -> entity.onUpdate(delta));
        colliders.forEach(collider -> colliders.stream()
                .filter(other -> other != collider && collider.overlaps(other))
                .forEach(collider::onCollision));
    }

    private void handleSpawnRequests() {
        if (entitiesToSpawn.isEmpty()) {
            return;
        }
        spawnedEntities.addAll(entitiesToSpawn);
        entitiesToSpawn.clear();
        spawnedEntities.forEach(entity -> entity.onSpawn(this));
        entities.addAll(spawnedEntities);
        spawnedEntities.stream()
                .filter(entity -> entity instanceof Collider)
                .forEach(collider -> colliders.add((Collider) collider));
        spawnedEntities.clear();
    }

    private void handleRemoveRequests() {
        entities.stream().filter(Entity::removalRequested).forEach(Entity::onDispose);
        colliders.removeIf(Collider::removalRequested);
        entities.removeIf(Entity::removalRequested);
    }

    public void render(SpriteBatch batch) {
        entities.stream()
                .sorted(Comparator.comparingInt(Entity::getSortingLayer))
                .forEach(entity -> entity.onRender(batch));
    }

    public void renderDebug(ShapeRenderer renderer) {
        entities.forEach(entity -> entity.onDebugRender(renderer));
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

    public GameMode getGameMode() {
        return gameMode;
    }
}
