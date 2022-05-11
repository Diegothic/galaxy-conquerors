package com.galaxy.game.entity.effects;

import com.badlogic.gdx.math.Vector2;
import com.galaxy.game.entity.enemy.Enemy;

public class EnemyMuzzleFlashEffect extends Effect {

    public EnemyMuzzleFlashEffect(Enemy enemy, Vector2 offset) {
        super("enemy/enemy_muzzle_flash_sheet.png",
                8, 8, 5,
                1.0f / 24.0f
        );
        attachTo(enemy, offset);
    }
}
