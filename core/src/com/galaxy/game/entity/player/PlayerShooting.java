package com.galaxy.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.effects.PlayerMuzzleFlashEffect;
import com.galaxy.game.entity.effects.PlayerReloadEffect;
import com.galaxy.game.entity.projectile.PlayerProjectile;
import com.galaxy.game.util.Cooldown;

public class PlayerShooting {

    private final Player player;
    private final Vector2 shootingPoint;
    private final Vector2 reloadBarOffset;
    private final float projectileSpeed;
    private final float projectileLifetime;
    private final Cooldown cooldown;

    public PlayerShooting(Player player, Vector2 shootingPoint, float cooldown) {
        this.player = player;
        this.shootingPoint = new Vector2();
        this.shootingPoint.set(shootingPoint);
        reloadBarOffset = new Vector2(0.0f, -11.0f);
        projectileSpeed = 200.0f;
        projectileLifetime = 3.0f;
        this.cooldown = new Cooldown(cooldown);
    }

    public void update(float delta) {
        if (isShootingKeyPressed() && cooldown.isReady()) {
            cooldown.reset();
            shoot();
        }
        cooldown.step(delta);
    }

    private void shoot() {
        var muzzleFlash = new PlayerMuzzleFlashEffect(player, shootingPoint);
        player.getLevel().spawn(muzzleFlash);
        var projectile = new PlayerProjectile(projectileSpeed, projectileLifetime);
        projectile.position.set(
                player.position.x + shootingPoint.x,
                player.position.y + shootingPoint.y
        );
        player.getLevel().spawn(projectile);
        var reloadBar = new PlayerReloadEffect(player, reloadBarOffset, cooldown.resetTime);
        player.getLevel().spawn(reloadBar);
    }

    private boolean isShootingKeyPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }
}
