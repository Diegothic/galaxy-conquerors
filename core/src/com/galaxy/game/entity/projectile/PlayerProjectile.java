package com.galaxy.game.entity.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxy.game.entity.player.Player;
import com.galaxy.game.graphics.AnimatedSprite;

public class PlayerProjectile extends Projectile {

    private final AnimatedSprite sprite;

    public PlayerProjectile(float speed, float lifetime) {
        super(0.0f, 1.0f, speed, 8.0f, 8.0f, lifetime);
        sprite = new AnimatedSprite("player/player_projectile_sheet.png",
                8, 8, 7,
                1.0f / 12.0f
        );
        sprite.setLooping(true);
        setOnCollision(other -> {
            if (other.getParent() instanceof Player) {
                return;
            }
            if (other.getParent() instanceof Projectile) {
                return;
            }
            //TODO:: Destroy other if others parent is Enemy :: Enemy class required
            getLevel().destroy(this);
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
        //TODO:: Explosion
    }

    @Override
    public void onDispose() {
        super.onDispose();
        sprite.dispose();
    }
}
