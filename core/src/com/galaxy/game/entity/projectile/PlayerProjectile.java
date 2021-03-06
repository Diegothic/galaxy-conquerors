package com.galaxy.game.entity.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.Shield;
import com.galaxy.game.entity.effects.PlayerProjectileHitEffect;
import com.galaxy.game.entity.enemy.Enemy;
import com.galaxy.game.entity.ship.Ship;
import com.galaxy.game.graphics.AnimatedSprite;
import com.galaxy.game.score.Score;

public class PlayerProjectile extends Projectile {

    private final AnimatedSprite sprite;
    public Score score = new Score(Score.getPoints());

    public PlayerProjectile(float speed, float lifetime) {
        super(0.0f, 1.0f, speed, new Vector2(4.0f, 6.0f), lifetime);
        sprite = new AnimatedSprite("player/player_projectile_sheet.png",
                8, 8, 7,
                1.0f / 12.0f
        );
        sprite.setLooping(true);
        setOnCollision(other -> {
            if (other.getParent() instanceof Enemy) {
                collider.enabled = false;
                score.addPoints(((Enemy) (other.getParent())));
                getLevel().getGameMode().decreaseAliveEnemies();
                getLevel().destroy(other.getParent());
                getLevel().destroy(this);
                spawnHitEffect();
            }
            if (other.getParent() instanceof Ship) {
                collider.enabled = false;
                score.addPoints(500);
                Ship ship = (Ship) other.getParent();
                ship.explode();
                if (ship.isAlive) {
                    getLevel().destroy(this);
                    spawnHitEffect();
                }
            }
            if (other.getParent() instanceof Shield) {
                collider.enabled = false;
                getLevel().destroy(this);
                spawnHitEffect();
            }
        });
    }

    private void spawnHitEffect() {
        var hitEffect = new PlayerProjectileHitEffect();
        hitEffect.position.set(position);
        hitEffect.position.y += sprite.getHeight() / 2.0f;
        getLevel().spawn(hitEffect);
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        sprite.step(delta);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        sprite.setPosition(
                position.x - sprite.getWidth() / 2.0f,
                position.y - sprite.getHeight() / 2.0f
        );
        sprite.draw(batch);
    }

    @Override
    public void onDispose() {
        super.onDispose();
        sprite.dispose();
    }
}
