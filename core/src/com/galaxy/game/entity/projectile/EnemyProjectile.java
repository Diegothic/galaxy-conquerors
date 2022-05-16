package com.galaxy.game.entity.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.effects.EnemyProjectileHitEffect;
import com.galaxy.game.entity.player.Player;
import com.galaxy.game.graphics.AnimatedSprite;

public class EnemyProjectile extends Projectile {

    private final AnimatedSprite sprite;

    public EnemyProjectile() {
        super(0.0f, -1.0f, 150.0f, new Vector2(4.0f, 6.0f), 3.0f);
        sprite = new AnimatedSprite("enemy/enemy_projectile_sheet.png",
                8, 8, 6,
                1.0f / 12.0f
        );
        sprite.setLooping(true);
        setOnCollision(other -> {
            if (other.getParent() instanceof Player) {
                collider.enabled = false;
                Player player = (Player) other.getParent();
                player.explode();
                getLevel().destroy(this);
            }
        });
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
    public void onDestroy() {
        super.onDestroy();
        var hitEffect = new EnemyProjectileHitEffect();
        hitEffect.position.set(position);
        hitEffect.position.y -= sprite.getHeight() / 2.0f;
        getLevel().spawn(hitEffect);
    }

    @Override
    public void onDispose() {
        super.onDispose();
        sprite.dispose();
    }
}
